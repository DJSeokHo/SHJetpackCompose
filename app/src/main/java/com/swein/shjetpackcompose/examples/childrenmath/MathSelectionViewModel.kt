package com.swein.shjetpackcompose.examples.childrenmath

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MathSelectionViewModel: ViewModel() {

    val addition = mutableStateOf(true)

    val subtraction = mutableStateOf(false)

    val multiplication = mutableStateOf(false)

    val division = mutableStateOf(false)

}