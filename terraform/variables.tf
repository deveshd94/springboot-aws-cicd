variable "aws_region" {
  description = "AWS region for the deployment."
  type        = string
  default     = "us-west-2"
}

variable "project_name" {
  description = "Name prefix used for AWS resources."
  type        = string
  default     = "book-search"
}

variable "container_port" {
  description = "Port exposed by the Spring Boot container."
  type        = number
  default     = 8080
}

variable "desired_count" {
  description = "Number of ECS tasks Terraform starts with. Keep 0 for first apply; CI/CD will scale to 1 after first image deploy."
  type        = number
  default     = 0
}

variable "task_cpu" {
  description = "Fargate task CPU units."
  type        = number
  default     = 512
}

variable "task_memory" {
  description = "Fargate task memory in MiB."
  type        = number
  default     = 1024
}
