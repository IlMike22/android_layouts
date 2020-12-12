package com.raywenderlich.android.octomembers.ui.teammembers

import com.raywenderlich.android.octomembers.model.Member


interface TeamMembersContract {

  interface View {
    fun showMembers(members: List<Member>)
    fun showErrorRetrievingMembers()
    fun clearMembers()
    fun showLoading()
    fun hideLoading()
    fun disableInput()
    fun enableInput()
    fun showEmptyState(teamName:String)
    fun hideEmptyState()
  }

  interface Presenter {
    fun retrieveAllMembers(teamName: String)
  }
}