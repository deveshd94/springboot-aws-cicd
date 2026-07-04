# Spring Boot AWS CI/CD Starter

This project is a small end-to-end DevOps learning project:

- Spring Boot web app
- Docker image
- GitHub Actions CI/CD pipeline
- AWS infrastructure managed with Terraform
- ECR + ECS Fargate + Application Load Balancer + CloudWatch logs

## Architecture

```text
GitHub push
  -> GitHub Actions
  -> Maven tests
  -> Docker build
  -> Push image to ECR
  -> Update ECS Fargate service
  -> App served through ALB
```

## Run Locally

```bash
mvn spring-boot:run
curl http://localhost:8080/
curl "http://localhost:8080/books?q=java"
```

Or with Docker:

```bash
docker compose up --build
curl http://localhost:8080/books
```

## Deploy AWS Infra

From the Terraform directory:

```bash
cd terraform
cp terraform.tfvars.example terraform.tfvars
terraform init
terraform plan
terraform apply
```

Terraform creates:

- VPC with 2 public subnets
- Internet gateway and public route table
- ECR repository
- ECS cluster
- ECS task definition
- ECS service
- Application Load Balancer
- Security groups
- CloudWatch log group

## GitHub Secrets

Add these repository secrets in GitHub:

```text
AWS_ACCESS_KEY_ID
AWS_SECRET_ACCESS_KEY
```

The IAM user/role behind those credentials needs permissions for:

- ECR push
- ECS describe/register/update service
- IAM pass role for the ECS execution role

For production, prefer GitHub OIDC instead of long-lived AWS access keys.

## CI/CD Flow

`.github/workflows/ci-cd.yml` does two things:

- On pull requests to `main`: runs Maven tests
- On pushes to `main`: runs tests, builds Docker image, pushes to ECR, deploys ECS service

## Important First Deploy Note

The Terraform ECS service starts with:

```text
desired_count = 0
```

That makes the first `terraform apply` succeed before an app image exists in ECR.
After the first push to `main`, GitHub Actions builds the Docker image, deploys a new
task definition, and scales the ECS service to 1 task.

## Cleanup

```bash
cd terraform
terraform destroy
```

The ECR repository uses `force_delete = true` so `terraform destroy` can clean up pushed images while you are learning.
