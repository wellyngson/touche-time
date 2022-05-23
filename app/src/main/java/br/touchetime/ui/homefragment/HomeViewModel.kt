package br.touchetime.ui.homefragment

import androidx.lifecycle.ViewModel
import br.touchetime.data.repository.AthleteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val athleteRepository: AthleteRepository,
) : ViewModel()
