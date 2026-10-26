package com.udistrital.appcivica.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_civica_db"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateTestUsers(database.userDao())
                    }
                }
            }

            private suspend fun populateTestUsers(userDao: UserDao) {
                val testUsers = listOf(
                    UserEntity(
                        email = "admin@appcivica.com",
                        passwordHash = "123456",
                        name = "Admin Cívico",
                        role = "ADMIN",
                        isVulnerable = false
                    ),
                    UserEntity(
                        email = "ciudadano@appcivica.com",
                        passwordHash = "123456",
                        name = "Carlos Mendoza",
                        role = "CIUDADANO",
                        isVulnerable = true
                    ),
                    UserEntity(
                        email = "monitoreo@appcivica.com",
                        passwordHash = "123456",
                        name = "Estación RMCAB",
                        role = "ANALISTA",
                        isVulnerable = false
                    )
                )
                userDao.insertAll(testUsers)
            }
        }
    }
}
