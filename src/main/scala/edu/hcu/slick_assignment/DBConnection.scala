package edu.hcu.slick_assignment

import slick.jdbc.MySQLProfile.api._

object DBConnection {
  def db = {
    Database.forURL("jdbc:mysql://localhost:3306/slick_assignment",
      driver = "com.mysql.jdbc.Driver",
      user = "root", password = "root")
  }

}


/*Map(Sonu -> Vector(Parallel Computing),
 Radha -> Vector(Data Structures, C language, DBMS),
   Sam -> Vector(Programming Methodology),
    Rakesh -> Vector(Algorithms, C language),
    Sneha -> Vector(Data Warehouse),
     Sanno -> Vector(Data Mining),
     Rekha -> Vector(C language),
     Sudha -> Vector(Computer Vision),
      Rani -> Vector(C language, DBMS),
       Raman -> Vector(Operating System, Computer Vision))
*/