package redmine4s.api.model

case class Tracker(id: Long, name: String, defaultStatus: Option[(Long, String)])
