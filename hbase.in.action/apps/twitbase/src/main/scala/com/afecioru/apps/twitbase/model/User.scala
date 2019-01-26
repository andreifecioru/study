package com.afecioru.apps.twitbase.model

case class User(
  userName: String,
  name: String,
  email: String,
  password: String,
  tweets: Long = 0L
)
