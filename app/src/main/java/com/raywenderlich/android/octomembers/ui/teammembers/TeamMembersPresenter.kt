package com.raywenderlich.android.octomembers.ui.teammembers

import com.raywenderlich.android.octomembers.model.Member
import com.raywenderlich.android.octomembers.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamMembersPresenter(val repository: Repository, val view: TeamMembersContract.View) : TeamMembersContract.Presenter {
    override fun retrieveAllMembers(teamName: String) {
        showViewLoadingState()
        repository.retrieveTeamMembers(teamName, object : Callback<List<Member>> {
            override fun onResponse(call: Call<List<Member>>?, response: Response<List<Member>>?) {
                val members = response?.body()
                if (members != null) {
                    if (members.isEmpty()) {
                        showEmptyState(teamName)
                    } else {
                        hideEmptyState()
                        showMembersInView(members)
                    }
                } else {
                    clearViewMembersAndShowError()
                }
            }

            override fun onFailure(call: Call<List<Member>>?, t: Throwable?) {
                clearViewMembersAndShowError()
            }
        })
    }

    private fun clearViewMembersAndShowError() {
        view.clearMembers()
        view.showErrorRetrievingMembers()
        view.enableInput()
        view.hideLoading()
    }

    private fun showViewLoadingState() {
        view.showLoading()
        view.disableInput()
    }

    private fun showMembersInView(members: List<Member>) {
        view.showMembers(members)
        view.hideLoading()
        view.enableInput()
    }

    private fun showEmptyState(teamName:String) {
        view.showEmptyState(teamName)
    }

    private fun hideEmptyState() {
        view.hideEmptyState()
    }
}