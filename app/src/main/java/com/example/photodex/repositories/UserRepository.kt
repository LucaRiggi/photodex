
import com.example.photodex.data.user.User
import com.example.photodex.data.user.UserDAO
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDAO) {
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

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