# Code Challenge: Soccer match stats

## TODOs

* Resolve `TODO`s comments in code
* Test for invalid and missing XML tags
* Add test coverage report
* Handle all resources in brackets to close them gracefully
* Add graceful-shutdown
* Add observability data (logs, metrics, tracing)
* Parse inputs using Console

## Getting Started

Instructions to run the code on your local machine for development and testing purposes.

### Prerequisites

Please install [sbt: The interactive build tool](https://www.scala-sbt.org/) from [here](https://www.scala-sbt.org/download.html)
in order to build, compile, test and run the code.

For an easier way of running the service, please install [Docker](https://docs.docker.com/get-docker/) and [Docker Compose](https://docs.docker.com/compose/install/) 

## Building

### From sources

Run `sbt stage`, this will create a launcher at `eem\modules\cli\target\universal\scripts\bin\eem-cli`.

### Docker image

Run `sbt docker:publishLocal`, this will pull `openjdk:18-slim-buster` and build `eem-cli:0.1.0` images.
You can check it out running `docker images`.

## Running

### From sbt

You can run the code directly from sbt-shell typing `sbt` and then `cli/run leftside_pass "./matchresults.xml"`. 

### From a docker container

If you already created `eem-cli:0.1.0` image using `sbt docker:publishLocal`, you can run it in the interactive mode:

```
cat leftside_pass "./matchresults.xml" | docker run -i eem-cli:0.1.0 
```

Or just use `docker-compose up` and pass the requeired arguments.

### Unit tests

Unit tests code is under `eem/modules/tests`. To run them use `sbt test`.

### Built With

This project is powered by the following libraries:

* [cats](https://typelevel.org/cats/): Lightweight, modular, and extensible library for functional programming
* [catsEffects](https://typelevel.org/cats-effect/): The IO monad for Scala
* [fs2](https://fs2.io/): Purely functional, effectful, resource-safe, concurrent streams for Scala
* [xml-spac](https://github.com/dylemma/xml-spac): Handle streaming XML data with declarative, composable parsers

## License

This project is licensed under the GNU License - see the [LICENSE](LICENSE) file for details.
