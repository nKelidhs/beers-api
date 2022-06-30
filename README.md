[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/nKelidhs/beers-api">
    ![alt text](https://github.com/nKelidhs/beers-api/beer-log/png?raw=true)
  </a>

<h3 align="center">project_title</h3>

  <p align="center">
    Beers RESTful API
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

The API handles beers with the basic CRUD operations. It is build with Java using the Spring Framework. The database that is PostgreSQL.

This project was created as a job assignment.

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

* [Java](https://www.java.com/en/)
* [Spring Framework](https://spring.io/)
* [Lombok](https://projectlombok.org/)
* [PostgreSQL](https://www.postgresql.org/)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple example steps.

### Prerequisites

In order to run the API, PostgreSQL must be installed in the OS.

* [PostgreSQL](https://www.postgresql.org/download/)

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/nKelidhs/beers-api.git
   ```
2. Create the Postgre Database
   ```bash
   CREATE DATABASE database_name;
   ```
3. Enter your Postgre Database URL, Username and Password in `resources/application.properties`
   ```
   spring.datasource.url=jdbc:postgresql://localhost:PORT/database_name
   spring.datasource.username=username
   spring.datasource.password=password
   ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Swagger was implemented in the API for testing purposes. For the Swagger please follow the link after you run the project: http://localhost:8080/swagger-ui/#/

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Nikos Kelidis - [LinkedIn](https://www.linkedin.com/in/nkelidis1)- nkelidhs@gmail.com

Project Link: [https://github.com/nKelidhs/beers-api.git](https://github.com/nKelidhs/beers-api.git)

<p align="right">(<a href="#top">back to top</a>)</p>




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/nkelidis1
[product-screenshot]: beer-logo.png
