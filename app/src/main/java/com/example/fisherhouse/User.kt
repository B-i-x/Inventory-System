package com.example.fisherhouse

class Users() {


    var user_list = mutableMapOf<String, User>()

    fun addUser(title: String, username: String, pass: String) {

        val new_user = User(username, pass)

        user_list[title] = new_user
    }

    fun is_credentials_valid(test_username: String, test_pass: String): Boolean {

        user_list.forEach { (title, user) ->
            if ((user.username == test_username) and (user.pass == test_pass)) {
                return true
            }
        }

        return false
    }
}

class User(var username: String, var pass: String) {}