package com.plinfall.game.domain

import android.content.Context

class Prefs(private val context: Context) {
    private val sp = context.getSharedPreferences("SP", Context.MODE_PRIVATE)

    fun getBalance(): Int = sp.getInt("BALANCE", 1000)
    fun setBalance(balance: Int) = sp.edit().putInt("BALANCE", getBalance() + balance).apply()

    fun getLastBalance(): Long = sp.getLong("LAST", 0)
    fun setLastBalance(lastBalance: Long) = sp.edit().putLong("LAST", lastBalance).apply()
}