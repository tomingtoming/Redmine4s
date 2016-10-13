package redmine4s.api.model

import org.joda.time.DateTime
import org.slf4j.{Logger, LoggerFactory}
import redmine4s.Redmine

sealed trait Project {
  protected val logger: Logger
  protected val redmine: Redmine

  val id: Long
  val name: String
  val description: Option[String]
  val homepage: Option[String]
  val isPublic: Boolean
  val parent: Option[(Long, String)]
  val createdOn: DateTime
  val updatedOn: DateTime
  val identifier: String

  def show: Project = redmine.showProject(this.id)

  def update(name: Option[String] = None,
             description: Option[String] = None,
             homepage: Option[String] = None,
             isPublic: Option[Boolean] = None,
             parent: Option[Long] = None,
             inheritMembers: Option[Boolean] = None,
             trackers: Option[Seq[Long]] = None,
             issueCategories: Option[Seq[Long]] = None,
             enabledModules: Option[Seq[String]] = None): Project = {
    redmine.updateProject(Right(identifier), name, description, homepage, isPublic, parent, inheritMembers, trackers, issueCategories, enabledModules)
  }

  def delete(): Unit = redmine.deleteProject(identifier)

  def listProjectMemberships: Iterable[ProjectMembership] = redmine.listProjectMemberships(this.id)

  def listVersions: Iterable[Version] = redmine.listVersions(this.id)

  def listIssueCategories: Iterable[IssueCategory] = redmine.listIssueCategories(this.id)

  def listWiki(baseUrl: String): Iterable[Wiki] = redmine.listWikis(this.id)

  def listNews(baseUrl: String): Iterable[News] = redmine.listNews(this.id)
}

trait ProjectDetail extends Project {
  val trackers: Seq[(Long, String)]
  val issueCategories: Seq[(Long, String)]
}

case class ProjectSummary(id: Long,
                          name: String,
                          description: Option[String],
                          homepage: Option[String],
                          isPublic: Boolean,
                          parent: Option[(Long, String)],
                          createdOn: DateTime,
                          updatedOn: DateTime,
                          identifier: String,
                          redmine: Redmine) extends Project {
  protected val logger = LoggerFactory.getLogger(this.getClass)
}

case class ProjectDetail25(id: Long,
                           name: String,
                           description: Option[String],
                           homepage: Option[String],
                           isPublic: Boolean,
                           parent: Option[(Long, String)],
                           createdOn: DateTime,
                           updatedOn: DateTime,
                           identifier: String,
                           trackers: Seq[(Long, String)],
                           issueCategories: Seq[(Long, String)],
                           redmine: Redmine) extends ProjectDetail {
  protected val logger = LoggerFactory.getLogger(this.getClass)
}

case class ProjectDetail26(id: Long,
                           name: String,
                           description: Option[String],
                           homepage: Option[String],
                           isPublic: Boolean,
                           parent: Option[(Long, String)],
                           createdOn: DateTime,
                           updatedOn: DateTime,
                           identifier: String,
                           trackers: Seq[(Long, String)],
                           issueCategories: Seq[(Long, String)],
                           enabledModules: Seq[String],
                           redmine: Redmine) extends ProjectDetail {
  protected val logger = LoggerFactory.getLogger(this.getClass)
}
