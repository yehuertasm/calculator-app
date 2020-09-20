package com.yehm.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    var lastNumeric = false
    var lastDot = false
    private lateinit var textViewInput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        textViewInput = findViewById(R.id.textView_input)
    }

    fun onDigit(view: View) {
        textViewInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        textViewInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            textViewInput.append((view as Button).text)
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var textViewValue = textViewInput.text.toString()
            var prefix = ""
            try {
                if (textViewValue.startsWith("-")) {
                    prefix = "-"
                    textViewValue = textViewValue.substring(1)
                }
                when {
                    textViewValue.contains("-") -> {
                        val splitValue = textViewValue.split("-")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        textViewInput.text =
                            removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    textViewValue.contains("+") -> {
                        val splitValue = textViewValue.split("+")
                        val one = splitValue[0]
                        val two = splitValue[1]

                        textViewInput.text =
                            removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                    textViewValue.contains("*") -> {
                        val splitValue = textViewValue.split("*")
                        val one = splitValue[0]
                        val two = splitValue[1]

                        textViewInput.text =
                            removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    textViewValue.contains("/") -> {
                        val splitValue = textViewValue.split("/")
                        val one = splitValue[0]
                        val two = splitValue[1]

                        textViewInput.text =
                            removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {

            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if ((value.toDouble() % 1) == 0.0) {
            value = value.toDouble().toInt().toString()
        }
        return value
    }

    fun onOperator(view: View) {
        if (textViewInput.text.isEmpty()) {
            textViewInput.append((view as Button).text)
        } else if (lastNumeric && !isOperatorAdded(textViewInput.text.toString())) {
            textViewInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains(Regex("[-+*/]"))
        }
    }
}