package com.raywenderlich.android.octomembers.ui.member

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.octomembers.R
import com.raywenderlich.android.octomembers.model.Member
import com.raywenderlich.android.octomembers.repository.remote.RemoteRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_member.*


class MemberActivity : AppCompatActivity(), MemberContract.View {
    lateinit var presenter: MemberContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        setupPresenter()
        setupActionBar()

        presenter.retrieveMember(memberLoginFromIntent())
    }

    override fun showMember(member: Member) {
        Picasso.get().load(member.avatarUrl).into(memberAvatar)
        val isMemberName = !member.name.isNullOrBlank()
        if (isMemberName) {
            memberName.text = member.name
        }

        val isMemberCompany = !member.company.isNullOrBlank()
        if (isMemberCompany) {
            memberCompany.text = member.company
            memberCompanyContainer.visibility = View.VISIBLE
        }

        val isMemberEmail = !member.email.isNullOrBlank()
        if (isMemberEmail) {
            memberEmail.text = member.email
            memberEmailContainer.visibility = View.VISIBLE
        }

        memberLogin.text = member.login
        memberType.text = member.type
    }

    override fun showErrorRetrievingMember() {
        Toast.makeText(this, getString(R.string.error_retrieving_member), Toast.LENGTH_SHORT).show()
    }

    private fun setupPresenter() {
        presenter = MemberPresenter(RemoteRepository(), this)
    }

    private fun setupActionBar() {
        title = memberLoginFromIntent()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun memberLoginFromIntent() = intent.getStringExtra(EXTRA_MEMBER_LOGIN)

    companion object {
        const val EXTRA_MEMBER_LOGIN = "EXTRA_MEMBER_LOGIN"

        fun newIntent(context: Context, memberLogin: String): Intent {
            val intent = Intent(context, MemberActivity::class.java)
            intent.putExtra(EXTRA_MEMBER_LOGIN, memberLogin)
            return intent
        }
    }
}
