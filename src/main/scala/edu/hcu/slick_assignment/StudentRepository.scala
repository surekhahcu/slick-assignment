package edu.hcu.slick_assignment

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

class StudentRepository {


  val studentTableQuery = TableQuery[Students]

  def create(student: Student): Future[Int] =
    DBConnection.db.run {
      (studentTableAutoInc += student)

    }

  def studentTableAutoInc = studentTableQuery returning studentTableQuery.map(_.id)

  def delete(id: Int): Future[Int] = DBConnection.db.run {
    studentTableQuery.filter(_.id === id).delete
  }

  def update(student: Student): Future[Int] = DBConnection.db.run {
    studentTableQuery.filter(_.id === student.id.get).update(student)
  }

  def getById(id: Int): Future[Option[Student]] = DBConnection.db.run {
    studentTableQuery.filter(_.id === id).result.headOption
  }

  def getAll(): Future[List[Student]] = DBConnection.db.run {
    studentTableQuery.to[List].result
  }

  class Students(tag: Tag) extends Table[Student](tag, "Students") {
    def * = (name, email, address, id.?) <> (Student.tupled, Student.unapply)

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def email = column[String]("email")

    def address = column[String]("address")
  }

  // Await.result(DBConnection.db.run(studentTableQuery.schema.create), 10 seconds)

}


case class Student(name: String, email: String, address: String, id: Option[Int] = None)