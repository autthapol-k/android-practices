package com.autthapol.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.progressindicator.LinearProgressIndicator

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var questions: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mUserName: String? = null
    private var mCorrectAnswer: Int = 0

    private var ivImage: ImageView? = null
    private var progressBar: LinearProgressIndicator? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var tvOption1: TextView? = null
    private var tvOption2: TextView? = null
    private var tvOption3: TextView? = null
    private var tvOption4: TextView? = null
    private var btnSubmit: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz_question)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        ivImage = findViewById(R.id.iv_image)
        progressBar = findViewById(R.id.progress_bar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        tvOption1 = findViewById(R.id.tv_option1)
        tvOption2 = findViewById(R.id.tv_option2)
        tvOption3 = findViewById(R.id.tv_option3)
        tvOption4 = findViewById(R.id.tv_option4)
        btnSubmit = findViewById(R.id.btn_submit)

        tvOption1?.setOnClickListener(this)
        tvOption2?.setOnClickListener(this)
        tvOption3?.setOnClickListener(this)
        tvOption4?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        questions = Constants.getQuestions()

        setQuestion()
    }

    private fun setQuestion() {
        defaultOptionView()

        val question = questions!![mCurrentPosition - 1]
        progressBar?.progress = mCurrentPosition
        ivImage?.setImageResource(question.image)
        tvProgress?.text = "$mCurrentPosition / ${progressBar?.max}"
        tvQuestion?.text = question.question
        tvOption1?.text = question.option1
        tvOption2?.text = question.option2
        tvOption3?.text = question.option3
        tvOption4?.text = question.option4

        if (mCurrentPosition == questions!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "SUBMIT"
        }
    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()

        tvOption1?.let {
            options.add(0, it)
        }
        tvOption2?.let {
            options.add(1, it)
        }
        tvOption3?.let {
            options.add(2, it)
        }
        tvOption4?.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7a8089"))
            option.typeface = Typeface.DEFAULT
            option.background =
                ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectOptionView(tv: TextView, selectedOptionNumber: Int) {
        defaultOptionView()

        mSelectedOptionPosition = selectedOptionNumber

        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_option1 -> {
                tvOption1?.let {
                    selectOptionView(it, 1)
                }
            }

            R.id.tv_option2 -> {
                tvOption2?.let {
                    selectOptionView(it, 2)
                }
            }

            R.id.tv_option3 -> {
                tvOption3?.let {
                    selectOptionView(it, 3)
                }
            }

            R.id.tv_option4 -> {
                tvOption4?.let {
                    selectOptionView(it, 4)
                }
            }

            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= questions!!.size -> setQuestion()
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java).apply {
                                putExtra(Constants.USER_NAME, mUserName)
                                putExtra(Constants.CORRECT_ANSWER, mCorrectAnswer)
                                putExtra(Constants.TOTAL_QUESTION, questions?.size)
                            }
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = questions?.get(mCurrentPosition - 1)
                    if (question!!.answer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswer++
                    }
                    answerView(question.answer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == questions!!.size) {
                        btnSubmit?.text = "FINISH"
                    } else {
                        btnSubmit?.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tvOption1?.background = ContextCompat.getDrawable(this, drawableView)
            }

            2 -> {
                tvOption2?.background = ContextCompat.getDrawable(this, drawableView)
            }

            3 -> {
                tvOption3?.background = ContextCompat.getDrawable(this, drawableView)
            }

            4 -> {
                tvOption4?.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }
}