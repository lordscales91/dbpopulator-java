Database Populator for Java
===

This project is still a work in progress (WIP) and currently it's just a dirty draft to show the workflow. The aim of this project is to provide an extensible & customizable framework to generate dummy data and populate a database with it. This is done by the combination of a database populator(DBPopulator) and generators.

The Workflow in detail
---

The process starts by defining a database descriptor, which represents the structure of the database, as well as the required connection parameters. This information will be used by the populator to actually submit the generated data to the database, as well as exporting the data as a script.

The populator will delegate the generation of data to the generators, through their `generate` method. Each generator will produce dummy data for a specific field (or a set of fields). The populator on its turn will pack the generated data into the corresponding entries (e.g. rows) and return them back to the caller.

Why reinvent the wheel once more?
---

I know there are already some solutions out there, but most of them just limit to generate a script, at least the free ones, there are some commercial tools that actually submit the data to the database. However, even the commercial tools are a bit limited, for example they only work with a specific database type (e.g. SQL Server).

This software is designed in such a way that once it matures it will provide:

* Support for multiple databases, even NoSQL ones.
* Support for populating foreign keys.
* Support for multiple backends to generate the data.
* Support for running the library as a command line program (or even with a GUI).