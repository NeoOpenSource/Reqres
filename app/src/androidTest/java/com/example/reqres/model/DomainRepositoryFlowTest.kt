package com.example.reqres.model

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.reqres.database.FreeDB
import com.example.reqres.database.UserDao
import com.example.reqres.utils.TrampolineSchedulerProvider
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit


class DomainRepositoryFlowTest {
    private lateinit var db:FreeDB
    private lateinit var userDao: UserDao
    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, FreeDB::class.java).build()
        userDao = db.userDao()
    }

    @Test
    fun readUserApi() {
        val schedulerProvider = TrampolineSchedulerProvider()
        val repository = MockDataRepository()
        repository.setISchedulerProvider(schedulerProvider)
        val domainRepository = DomainRepository(repository,userDao)
        domainRepository.setISchedulerProvider(schedulerProvider)
        val result = domainRepository.readUserApi().test()
        schedulerProvider.scheduler.advanceTimeBy(2,TimeUnit.SECONDS)
        val ansData:List<UserInformation> = listOf(UserInformation("0","1","1","Wan_1","Tom_1"),
            UserInformation("1","2","2","Wan_2","Tom_2"))
        result.assertResult(DataResponseState.Success(ansData))
    }

    @After
    fun closeDb() {
        db.close()
    }
}