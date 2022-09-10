import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestSamples {
    private companion object {
        @JvmStatic
        val BOB_NAME = "Bob"

        @JvmStatic
        val JON_NAME = "Jon"
    }

    private val extService = mockk<ExternalService>()

    @InjectMockKs
    private var program: MyProgram = MyProgram(extService)

    @Test
    fun firstTest() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun myProgram() {
        every { extService.processRequest(BOB_NAME) } returns ResponseStatus.ACCEPT
        every { extService.processRequest(JON_NAME) } returns ResponseStatus.DECLINE

        assertEquals("$BOB_NAME, your request was accepted.", program.request(BOB_NAME))
        assertEquals("$JON_NAME, your request was declined.", program.request(JON_NAME))
    }

}