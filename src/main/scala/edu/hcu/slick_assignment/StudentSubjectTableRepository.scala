package edu.hcu.slick_assignment


import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future


class StudentSubjectTableRepository {

  val students = new StudentRepository

  val subjects = new SubjectRepository

  val studentSubjectTableQuery = TableQuery[SubTableForIds]

  def save(studentSubject: StudentSubject): Future[Int] =
    DBConnection.db.run {
      (studentSubjectTableQuery += studentSubject)
    }

  def studentWithSubject() = {
    val query =
      students.studentTableQuery.join(studentSubjectTableQuery)
        .on { case (student, studentSubject) => student.id === studentSubject.studentId }
        .join(subjects.subjectTableQuery)
        .on { case ((student, studentSubject), subject) => studentSubject.subjectId === subject.id }
        .map { case ((student, studentSubject), subject) => (student.name, subject.name) }

    DBConnection.db.run(query.result)
  }


  /*  def studentWithSubjectList() = {
      val query =
        students.studentTableQuery.join(studentSubjectTableQuery)
          .on { case (student, studentSubject) => student.id === studentSubject.studentId }
          .join(subjects.subjectTableQuery)
          .on { case ((student, studentSubject), subject) => studentSubject.subjectId === subject.id }
          .map { case ((student, studentSubject), subject) => (student.name, subject.name) }
      val result = Await.result(DBConnection.db.run(query.result), 10 seconds)
      result.groupBy { case (name, subject) => name }.map { case (name, list) => (name, list.map { case (name, subject) => subject }) }

    }*/

  class SubTableForIds(tag: Tag) extends Table[StudentSubject](tag, "StudentSubject") {

    def idx = index("idx_a", (studentId, subjectId), unique = true)

    def subjectId = column[Int]("subjectId")

    def studentId = column[Int]("studentId")

    def * = (studentId, subjectId) <> (StudentSubject.tupled, StudentSubject.unapply)

    def studentIdFKey = foreignKey("STU_FK", studentId, students.studentTableQuery)(_.id, onUpdate = ForeignKeyAction.Restrict)

    def subjectIdFKey = foreignKey("SUB_FK", subjectId, subjects.subjectTableQuery)(_.id, onUpdate = ForeignKeyAction.Restrict)


  }


}

case class StudentSubject(studentId: Int, subjectId: Int)