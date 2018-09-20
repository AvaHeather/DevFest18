package droiddevelopers254.devfestnairobi.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import droiddevelopers254.devfestnairobi.datastates.AuthenticateUserState
import droiddevelopers254.devfestnairobi.models.UserModel

class AuthenticateUserRepo {

    fun checkUserExistence(firebaseUser: FirebaseUser): LiveData<AuthenticateUserState> {
        val userStateMutableLiveData = MutableLiveData<AuthenticateUserState>()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("users").document(firebaseUser.uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val documentSnapshot = task.result
                        if (documentSnapshot.exists()) {
                            //user already exists do nothing
                            userStateMutableLiveData.value = AuthenticateUserState(true)
                        } else {
                            val user = UserModel(
                                     firebaseUser.uid,
                                    null,
                                    firebaseUser.email.toString(),
                                    firebaseUser.displayName.toString(),
                                    firebaseUser.photoUrl.toString()
                            )
                            userStateMutableLiveData.value = AuthenticateUserState(false,null,user)

                            //save user in firestore
                            firebaseFirestore.collection("users").document(user.user_id)
                                    .set(user)
                                    .addOnSuccessListener {
                                        userStateMutableLiveData.setValue(AuthenticateUserState(true)) }
                                    .addOnFailureListener {
                                        userStateMutableLiveData.setValue(AuthenticateUserState(false,it.message)) }

                        }
                    }

                }
        return userStateMutableLiveData
    }

}
