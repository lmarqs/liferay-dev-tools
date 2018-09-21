# liferay-dev-tools

This project provides a tooling to work with Liferay Portal development.

## Blade extensions:

Compatible with `blade-cli` version `3.1.0.201807032155`.

**Commands:**

Usage:

```.sh
$ blade <main class> [command] [command options]
```

Example:
Usage:

```.sh
$ blade eclipse:generate
```

| Command                | Feature   |
| ---------------------- | --------- |
| eclipse:generate       | Generates Eclipse IDE related artifacts and resolve the `.classpath` file dependencies using the `classes` directory instead of module reference |

