# Web-shop project
* [Project requirements](#requirements)
* [Project structure](#structure)
* [Local install](#local-install)
* [Author](#author)

# <a name="requirements"></a>Project Requirements
Simple project of web-shop with basic
 internet shop functionality, authentication, authorisation.
 Consumer can choose products from product list and put them to shopping cart. Possibility to complete orders is present.
 There are two user roles in project present: 'user' and 'admin'. Admin can create new or delete product being exist.
<hr>

# <a name="structure"></a>Project Structure
- JDK 1.11
- Maven 3.6.0
- javax.servlet-api 3.1.0
- maven-checkstyle-plugin 3.1.1
- jstl 1.2
- log4j 1.2.17
- mysql-connector-java 8.0.17
<hr>

# <a name="local-install"></a>Local Install
1. Use JDK 1.11+ in your project
2. Use Tomcat web-server:<br>
2.1  artifact internet-shop:war exploded;<br>
2.2  host address: http://localhost:8080/
3. Install MySQL Community Server 8
4. Change path in log4j.properties file to logs directory
5. Use sql statements in init_db.sql file to create schema and required tables

# <a name="author"></a>Author
Oleksii Lehedza

