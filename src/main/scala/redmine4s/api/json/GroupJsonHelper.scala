package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

trait GroupJsonHelper extends CustomFieldJsonHelper with ProjectMembershipJsonHelper {
  implicit val groupReads: Reads[Group] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'users).readNullable[Seq[(Long, String)]] ~
      (__ \ 'memberships).readNullable[Seq[ProjectMembership]] ~
      (__ \ 'custom_fields).readNullable[Seq[CustomFieldValue]]
    ) (Group.apply _)
  implicit val groupCreateWrites = (
    (__ \ 'name).write[String] ~
      (__ \ 'users).writeNullable[Seq[Long]]
    ).tupled
  implicit val groupUpdateWrites = (
    (__ \ 'name).writeNullable[String] ~
      (__ \ 'users).writeNullable[Seq[Long]]
    ).tupled
}
