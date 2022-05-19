terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
    }
  }
}

provider "aws" {
    region = "us-east-1"
}

resource "aws_instance" "app-server" {
    ami           = "ami-0ff8a91507f77f867"
    instance_type = "t2.micro"

    security_groups = [aws_security_group.allow_ssh.name]
    key_name        = "joancifuentes5@gmail.com"

    user_data = <<-EOF
    #!/bin/bash
    set -ex
    sudo yum update -y
    sudo yum install docker -y
    sudo service docker start
    sudo docker pull mysql
    sudo usermod -a -G docker ec2-user
    sudo docker run -d -p 13306:3306 --name mysql_containter -e MYSQL_ROOT_PASSWORD=root mysql:latest --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
    
    sudo docker network create --driver bridge my-net
    sudo network disconnect bridge mysql_container
    sudo network connect my-net mysql_container
    
    sudo docker run -d --network my-net -p 80:8080 yoan1x0/crapp:latest
  EOF

    user_data_replace_on_change = true

    tags = {
        Name = "joancifuentes5@gmail.com"
    }
}

resource "aws_security_group" "allow_ssh" {
  name        = "allow_http-1x0"
  description = "Allow HTTP inbound traffic"

  ingress {
    description      = "HTTP from VPC"
    from_port        = 22
    to_port          = 22
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  ingress {
    description      = "HTTP from VPC"
    from_port        = 80
    to_port          = 80
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "allow_http"
  }
}