import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AzureStorageService {
    @PUT
    @Headers("x-ms-blob-type: BlockBlob")
    fun uploadBlob(
        @Url url: String, // Full URL with SAS token
        @Body body: RequestBody
    ): Call<ResponseBody>

}
