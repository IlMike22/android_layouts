package com.raywenderlich.android.octomembers.ui.member

import com.raywenderlich.android.octomembers.model.Member
import com.raywenderlich.android.octomembers.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MemberPresenter(
        val repository: Repository,
        val view: MemberContract.View
) : MemberContract.Presenter {
    override fun retrieveMember(login: String) {
        repository.retrieveMember(login, object : Callback<Member> {
            override fun onResponse(call: Call<Member>?, response: Response<Member>?) {
                val member = response?.body()
                if (member != null) {
                    view.showMember(member)
                } else {
                    showErrorRetrievingMember()
                }
            }

            override fun onFailure(call: Call<Member>?, t: Throwable?) {
                showErrorRetrievingMember()
            }
        })
    }

    private fun showErrorRetrievingMember() {
        view.showErrorRetrievingMember()
    }
}