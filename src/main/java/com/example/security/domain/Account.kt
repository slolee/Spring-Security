package com.example.security.domain

import javax.persistence.*

@Entity
internal data class Account (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private val id: Long? = null,

        @Column(name = "ACCOUNT_USERNAME")
        private val username: String? = null,

        @Column(name = "ACCOUNT_LOGIN_ID")
        val userId: String? = null,

        @Column(name = "ACCOUNT_PASSWORD")
        val password: String? = null,

        @Column(name = "ACOCUNT_ROLE")
        // @Enumerated(value = EnumType.ORDINAL) // Index로 넣는다. 명시적이지 않아서 순서가 바뀌거나 나중에 유지보수하기 힘들다.
        @Enumerated(value = EnumType.STRING)
        val userRole: UserRole? = null,

        @Column(name = "ACCOUNT_SOCIAL_ID")
        private val socialID: Long? = null,

        @Column(name = "ACCOUNT_SOCIAL_PROFILEPIC")
        private val profileHref: String? = null
) {
    companion object { // Static 키워드를 대신해주는 역할.

    }
}