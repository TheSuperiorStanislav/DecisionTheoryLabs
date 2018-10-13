package com.study.thesuperiorstanislav.decisiontheorylabs.lecture1.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.data.source.Lecture1DataSource

class ChangeAlphaLecture1 (private val lecture1Repository: Lecture1DataSource): UseCase<ChangeAlphaLecture1.RequestValues, ChangeAlphaLecture1.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            val flag = requestValues.flag

            val value = if (flag){
                0.025
            }else{
                -0.025
            }

            lecture1Repository.changeAlpha(value, object : Lecture1DataSource.ChangeAlphaCallback {
                override fun onSaved() {
                    val responseValue = ResponseValue()
                    useCaseCallback?.onSuccess(responseValue)
                }

                override fun onError(error: Error) {
                    useCaseCallback?.onError(error)
                }
            })
        }
    }


    class RequestValues(val flag: Boolean) : UseCase.RequestValues

    class ResponseValue : UseCase.ResponseValue
}