package com.purple.aicalendar.data.repository

import com.purple.aicalendar.core.utils.AICalendarPreference
import com.purple.aicalendar.data.api.AICalenderApiServices
import com.purple.aicalendar.data.dao.EventDao
import com.purple.aicalendar.data.mapper.toEntity
import com.purple.aicalendar.domain.repository.TaskRepository
import javax.inject.Inject
import com.purple.aicalendar.domain.models.Event

class TaskRepositoryImpl  @Inject constructor(
    private val aiCalendarPreference: AICalendarPreference,
    private val eventDao: EventDao,
    private val apiService: AICalenderApiServices
) : TaskRepository {

    override suspend fun getPredictions(): Result<List<Event>> {
        val events = listOf(
            Event(
                id = "1",
                title = "Electricity Bill",
                amount = "12000",
                date = "2025-12-02",
                type = "Utility",
                transactionType = "Bill",
                accuracy = "95%",
                billName = "PHCN Electricity"
            ),
            Event(
                id = "2",
                title = "Water Bill",
                amount = "6000",
                date = "2025-12-03",
                type = "Utility",
                transactionType = "Bill",
                accuracy = "92%",
                billName = "Water Corporation"
            ),
            Event(
                id = "3",
                title = "Internet Subscription",
                amount = "18000",
                date = "2025-12-02",
                type = "Finance",
                transactionType = "Bill",
                accuracy = "97%",
                billName = "Airtel Unlimited"
            ),
            Event(
                id = "4",
                title = "Rent Payment",
                amount = "350000",
                date = "2025-12-04",
                type = "Expense",
                transactionType = "Transfer",
                accuracy = "99%",
                obligee = "Landlord - Mr. Daniel",
                accountNumber = "2219083345"
            ),
            Event(
                id = "5",
                title = "Salary Transfer",
                amount = "500000",
                date = "2025-12-10",
                type = "Finance",
                transactionType = "Transfer",
                accuracy = "98%",
                obligee = "Employee - John Mark",
                accountNumber = "0045598221"
            ),
            Event(
                id = "6",
                title = "Grocery Shopping",
                amount = "45000",
                date = "2025-12-12",
                type = "Expense",
                transactionType = "Transfer",
                accuracy = "91%",
                obligee = "Shoprite",
                accountNumber = "8821345678"
            ),
            Event(
                id = "7",
                title = "Gym Membership",
                amount = "15000",
                date = "2025-12-14",
                type = "Health",
                transactionType = "Bill",
                accuracy = "94%",
                billName = "Fitness+ Plan"
            ),
            Event(
                id = "8",
                title = "Fuel Purchase",
                amount = "20000",
                date = "2025-12-17",
                type = "Expense",
                transactionType = "Transfer",
                accuracy = "89%",
                obligee = "Total Filling Station",
                accountNumber = "6771239088"
            ),
            Event(
                id = "9",
                title = "DSTV Subscription",
                amount = "16000",
                date = "2025-12-20",
                type = "Utility",
                transactionType = "Bill",
                accuracy = "96%",
                billName = "DSTV Compact"
            ),
            Event(
                id = "10",
                title = "School Fees Payment",
                amount = "250000",
                date = "2025-12-22",
                type = "Education",
                transactionType = "Transfer",
                accuracy = "98%",
                obligee = "Greenfield Academy",
                accountNumber = "1394027811"
            )
        )
        return try {
            Result.success(events)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postEvents(kept: List<Event>,discarded: List<Event>): Result<Boolean>{
        return try {
            eventDao.insertEvents(kept.map {it.toEntity()})
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveEvents(kept:List<Event>,discarded: List<Event>,predictions: List<Event>) {
        aiCalendarPreference.saveList(kept = kept,discarded=discarded,predictions=predictions)
    }

    override suspend fun getKeptEvents():List<Event>{
        return aiCalendarPreference.getKeptList()
    }
    override suspend fun getDiscardedEvents():List<Event>{
        return aiCalendarPreference.getDiscardList()
    }

    override suspend fun getPredictionEvents():List<Event>{
        return aiCalendarPreference.getPredictionList()
    }

    override suspend fun saveRemainingEvents(events: List<Event>){
        try {
            eventDao.insertEvents(events.map{it.toEntity()})
//            Result.success(allEvent.map {it.toEvent()})
        } catch (_: Exception) {
//            Result.failure(e)
        }
    }

}