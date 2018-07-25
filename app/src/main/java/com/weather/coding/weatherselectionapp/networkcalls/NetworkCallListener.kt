package com.weather.coding.weatherselectionapp.networkcalls

interface NetworkCallListener<T> {
    fun onSuccess(model: T?)
    fun onFailure()
}