# liferay-dev-tools

Unleash the true power of development for Liferay Portal :muscle: :punch: :boom: :boom: :metal:

## Blade

Compatible with `blade-cli` version `3.1.0.201807032155`.


### Scripts

| File | Feature |
| -----| ------- |
| [install-blade.sh](https://github.com/lmarqs/liferay-dev-tools/blob/master/modules/blade/install-blade.sh) | Installs `blade-cli` locally `(-u)` version compatible with the project |

### Extensions

**Commands:**

Usage:

```.sh
$ blade <main class> [command] [command options]
```

Example:

```.sh
$ blade eclipse:generate
```

| Command                | Feature   |
| ---------------------- | --------- |
| eclipse:generate       | Generates Eclipse IDE related artifacts and resolve the `.classpath` file dependencies using the `classes` directory instead of module reference |

