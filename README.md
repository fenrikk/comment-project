# How-to-run

## Downloading __docker__ and __docker-compose__

- Install actual version of docker and docker-compose if it is not installed yet.
- You can download it from __https://docs.docker.com/engine/install/__
- After installing, use `docker run hello-world` to check if docker works correctly, if you see __Hello from Docker__ it
  works.

## Cloning project

- Clone project in your directory. `git clone https://github.com/fenrikk/comment-project.git`

## Starting docker containers

- After cloning configure server port in __application.properties__ file. By default, it is 80.
- In directory where is located __docker-compose.yml__ write down command `docker-compose up -d`
- After a few minutes of waiting, it will start on your docker visualizer.

