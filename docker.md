##Layer already being pulled by another client. Waiting.

```bash
$ docker-machine stop default
$ docker images -q | xargs docker rmi
$ docker-machine start default
```
