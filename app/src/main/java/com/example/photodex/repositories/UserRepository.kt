
import com.example.photodex.data.user.User
import com.example.photodex.data.user.UserDAO
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDAO) {
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }
    
    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }

    suspend fun updateUser(monkey: User) {
        userDao.update(monkey)
    }
}