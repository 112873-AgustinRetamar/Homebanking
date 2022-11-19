# Homebanking
Homebanking proyect devoloped for accenture/mindhub Java Bootcamp
java-- 17.0.2
javac-- 17.0.2
IDE-- IntelliJ
Gradle pryect, spring boot 2.7.5. Dependecies: Spring Data JPA, H2 database, Spring Web & Rest Repositories
The app is set to work with PostgreeSQL, follow the next steps:
1-On pgAdmin create database: homebanking
2-Create rol at Login/Group roles with name homebankingapp and password homebankingapp
3-In the properties of the homebanking database give ALL privileges to the user homebankingapp
*Otherwise is posible to use h2databse switching the comments from h2 to postgree, also possible to uncomment the initdata to start the app with data created but not advisable with PostgreSQL because it would repeat the data

