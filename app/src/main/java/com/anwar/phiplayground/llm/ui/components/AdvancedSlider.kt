package com.anwar.phiplayground.llm.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.text.DecimalFormat

@Composable
fun AdvancedSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int = 0,
    formatter: DecimalFormat = DecimalFormat("0")
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "$label: ${formatter.format(value)}")
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps
        )
    }
}