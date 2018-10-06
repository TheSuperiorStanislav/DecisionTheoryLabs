package com.study.thesuperiorstanislav.decisiontheorylabs.lab2.domain.usecase

import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.Gauss

class DoTheThing(): UseCase<DoTheThing.RequestValues, DoTheThing.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {

        }
    }

    class RequestValues() : UseCase.RequestValues

    class ResponseValue() : UseCase.ResponseValue
}
