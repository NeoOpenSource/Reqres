package com.example.reqres

import Data
import Support
import User
import com.example.reqres.database.UserDao
import com.example.reqres.database.UserTable
import com.example.reqres.model.*
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock


class DomainRepositoryUnitTest {
    @Mock private lateinit var http:IDataRepository
    @Mock private lateinit var db:UserDao
    @Mock private lateinit var domainRepository:IDomainRepository

    @Before
    fun before() {
        http = mock(IDataRepository::class.java)
        db = mock(UserDao::class.java)
        domainRepository = DomainRepository(http,db)
    }


    @Test
    fun `test database  read empty data`() {
        val state:DataResponseState<List<UserTable>> = DataResponseState.Success(arrayListOf())
        val result = domainRepository.userListIsEmpty(state).test()
        result.assertComplete()
    }

    @Test
    fun `test database  read have data`() {
        val testData:List<UserTable> = listOf(UserTable(0,"0","Wan","Tome","test","1"))
        val state:DataResponseState<List<UserTable>> = DataResponseState.Success(testData)
        val result = domainRepository.userListIsEmpty(state).test()
        result.assertResult(DataResponseState.Success(testData))
    }

    @Test
    fun `test user structure transform list UserTable`() {
        val listData:List<Data> = arrayListOf(Data("1","1","Wan_1",0,"Tom_1"),
            Data("2","2","Wan_2",1,"Tom_2"))
        val testUser = User(listData,0,0,Support("test","url"),0,0)
        val ansData:List<UserTable> = listOf(UserTable(id = "0", first_name = "Wan_1", last_name = "Tom_1", email = "1", avatar = "1"),
            UserTable(id = "1", first_name = "Wan_2", last_name = "Tom_2", email = "2", avatar = "2"))
        val result = domainRepository.toUserTable(testUser).test()
        result.assertResult(DataResponseState.Success(ansData))
    }

    @Test
    fun `test list UserTable transform list Information`() {
        val testData:List<UserTable> = listOf(UserTable(0,"0","Wan","Tome","test","1"),
            UserTable(1,"1","Wan","Tome","test","2"))
        val result = domainRepository.toUserList(DataResponseState.Success(testData)).test()
        val ansData:List<UserInformation> = listOf(UserInformation("0","1","test","Wan","Tome"),
            UserInformation("1","2","test","Wan","Tome"))
        result.assertResult(DataResponseState.Success(ansData))
    }

}