
import com.example.photodex.data.picture.Picture
import com.example.photodex.data.picture.PictureDAO
import kotlinx.coroutines.flow.Flow

class PictureRepository(private val pictureDAO: PictureDAO) {
    fun getAllPictures(): Flow<List<Picture>> = pictureDAO.getAllPictures()
    fun getPictureById(id: Int): Flow<Picture> = pictureDAO.getPictureById(id)

    suspend fun insertPicture(picture: Picture) {
        pictureDAO.insert(picture)
    }

    suspend fun deletePicture(picture: Picture) {
        pictureDAO.delete(picture)
    }

    suspend fun updatePicture(picture: Picture) {
        pictureDAO.update(picture)
    }
}