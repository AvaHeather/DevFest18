package droiddevelopers254.devfestnairobi.viewmodels

import android.arch.lifecycle.ViewModel

import droiddevelopers254.devfestnairobi.repository.RoomStarrSessionRepo

class StarrViewModel : ViewModel() {
    private val roomStarrSessionRepo: RoomStarrSessionRepo = RoomStarrSessionRepo()

}
