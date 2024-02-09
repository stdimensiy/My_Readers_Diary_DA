package ru.vdv.myapp.myreadersdiary.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.domain.Book
import ru.vdv.myapp.myreadersdiary.domain.User
import ru.vdv.myapp.myreadersdiary.glide.GlideImageLoader
import ru.vdv.myapp.myreadersdiary.glide.ImageLoader
import ru.vdv.myapp.myreadersdiary.ui.common.entities.*
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.OnViewReady
import ru.vdv.myapp.myreadersdiary.ui.common.interfaces.ToStatList
import ru.vdv.myapp.myreadersdiary.ui.common.viewholders.*

typealias uVH = UnknownTypeViewHolder
typealias mSSGroupSeparatorVH = MonthSummaryStatisticGroupSeparatorViewHolder
typealias mSSBookVH = MonthSummaryStatisticBookViewHolder

class SummaryStatisticAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //заглушка временно
    var items: List<ToStatList> = listOf(
        User(
            "DarthVerteliy",
            "DarthVerteliy",
            "https://dadapproves.ru/usercontent/avatars/da0000002.jpg",
            "https://dadapproves.ru/usercontent/bg/da_bg0000002.jpg"
        ),
        SummaryUserActivity(1232456),
        TotalMonthlyActivityHeader("Активность и достижения", "Декабрь 2021", 167852),
        StatisticsGroupSeparator("Прочтено книг (закончено)", 5),
        StatisticsGroupSeparator("В процессе чтения", 0),
        EventWithProgress(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Прочитано 40 из 100 страниц", 0, 40, "40%"
        ),
        EventWithProgress(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Прочитано 200 из 400 страниц", 20, 50, "50%"
        ),
        StatisticsGroupSeparator("добавлено новых книг", 6),
        SingleEvent(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Добавлена новая книга 410 стр / 92 000 слов."
        ),
        TotalMonthlyActivityFuter("Проба пера"),

        TotalMonthlyActivityHeader("", "Ноябрь 2021", 10256),
        StatisticsGroupSeparator("Прочтено книг (закончено)", 5),
        StatisticsGroupSeparator("В процессе чтения", 0),
        EventWithProgress(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Прочитано 100 из 400 страниц", 20, 50, "50%"
        ),
        EventWithProgress(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Прочитано 100 из 400 страниц", 20, 50, "50%"
        ),
        StatisticsGroupSeparator("добавлено новых книг", 6),
        TotalMonthlyActivityFuter("Проба пера"),
        TotalMonthlyActivityHeader("", "Октябрь 2021", 10256),
        StatisticsGroupSeparator("Прочтено книг (закончено)", 5),
        StatisticsGroupSeparator("В процессе чтения", 0),
        EventWithProgress(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Прочитано 100 из 400 страниц", 20, 50, "50%"
        ),
        EventWithProgress(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Прочитано 100 из 400 страниц", 20, 50, "50%"
        ),
        StatisticsGroupSeparator("добавлено новых книг", 6),
        TotalMonthlyActivityFuter("Проба пера"),
        TotalMonthlyActivityHeader("", "Сентябрь 2021", 10256),
        StatisticsGroupSeparator("Прочтено книг (закончено)", 5),
        StatisticsGroupSeparator("В процессе чтения", 0),
        EventWithProgress(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Прочитано 100 из 400 страниц", 20, 50, "50%"
        ),
        EventWithProgress(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Прочитано 100 из 400 страниц", 20, 50, "50%"
        ),
        StatisticsGroupSeparator("добавлено новых книг", 6),
        TotalMonthlyActivityFuter("Проба пера"),
        TotalMonthlyActivityHeader("", "Август 2021", 10256),
        StatisticsGroupSeparator("Прочтено книг (закончено)", 5),
        StatisticsGroupSeparator("В процессе чтения", 0),
        EventWithProgress(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Прочитано 100 из 400 страниц", 20, 50, "50%"
        ),
        EventWithProgress(
            Book(
                "0000000037",
                "Погружение в паттерны проектирования",
                "Швец",
                "Александр",
                "",
                "https://dadapproves.ru/usercontent/book/covers/pp123457.jpg"
            ), "Прочитано 100 из 400 страниц", 20, 50, "50%"
        ),
        StatisticsGroupSeparator("добавлено новых книг", 6),
        TotalMonthlyActivityFuter("Проба пера"),
    )
    private var onViewReadyListener: OnViewReady? = null
    private val imageLoader: ImageLoader<ImageView> = GlideImageLoader()

    private lateinit var eventGraphAdapter: ActivityStatisticsGraphAdapter
    lateinit var eventGraph: RecyclerView


    override fun getItemViewType(position: Int) = when (items[position]) {
        is EventWithProgress -> BaseViewType.USUAL
        is SingleEvent -> BaseViewType.SECONDARY_USUAL
        is User -> BaseViewType.USER_CARD
        is StatisticsGroupSeparator -> BaseViewType.GROUP_SEPARATOR
        is SummaryUserActivity -> BaseViewType.CONTENT
        is TotalMonthlyActivityFuter -> BaseViewType.FUTER
        is TotalMonthlyActivityHeader -> BaseViewType.HEADER
        else -> BaseViewType.UNKNOWN_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            BaseViewType.USER_CARD -> UserCardViewHolder(layoutInflater, parent)
            BaseViewType.CONTENT -> SummaryStatisticGraphViewHolder(layoutInflater, parent)
            BaseViewType.HEADER -> MonthSummaryStatisticHeaderViewHolder(layoutInflater, parent)
            BaseViewType.GROUP_SEPARATOR -> mSSGroupSeparatorVH(layoutInflater, parent)
            BaseViewType.USUAL -> MonthSummaryStatisticEventViewHolder(layoutInflater, parent)
            BaseViewType.SECONDARY_USUAL -> mSSBookVH(layoutInflater, parent)
            BaseViewType.FUTER -> MonthSummaryStatisticFuterViewHolder(layoutInflater, parent)
            else -> uVH(layoutInflater, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is UserCardViewHolder -> {
                item as User
                imageLoader.loadUserAvatar(item.avatarUrl, holder.userAvatar)
                imageLoader.loadUserBackground(item.backgroundUrl, holder.userBackground)
            }

            is SummaryStatisticGraphViewHolder -> {
                item as SummaryUserActivity
                eventGraphAdapter = ActivityStatisticsGraphAdapter()
                eventGraph = holder.eventGraph
                // вот именно сейчас необходимо сообщить выше о том что RV готов и готов принимать данные
                onViewReadyListener?.onViewReady()
            }

            is MonthSummaryStatisticHeaderViewHolder -> {
                item as TotalMonthlyActivityHeader
                holder.countDACoin.text = item.numValue.toString()
                holder.period.text = item.content
                holder.title.text = item.title
            }

            is mSSGroupSeparatorVH -> {
                item as StatisticsGroupSeparator
                holder.title.text = item.groupTitle
                holder.count.text = item.groupSummaryValue.toString()
            }

            is MonthSummaryStatisticEventViewHolder -> {
                item as EventWithProgress
                holder.bookTitle.text = item.baseObject.title
                holder.processTitle.text = item.eventDescription
                holder.processCount.text = item.eventFinalCount
                holder.process.progress = when (item.progressPercentPrimary) {
                    0 -> 1
                    in 1..100 -> item.progressPercentPrimary
                    else -> 100
                }
                holder.process.secondaryProgress = item.progressPercentSecondary
                imageLoader.loadBookCover(item.baseObject.bookCover, holder.bookCover)
            }

            is mSSBookVH -> {
                item as SingleEvent
                holder.bookTitle.text = item.baseObject.title
                holder.eventDescription.text = item.eventDescription
                imageLoader.loadBookCover(item.baseObject.bookCover, holder.bookCover)
            }

            is MonthSummaryStatisticFuterViewHolder -> {

            }

            else -> {
                holder as UnknownTypeViewHolder
                holder.title.text = buildString {
                    append("ID: ")
                    append(position)
                    append(", type: ")
                    append(item.javaClass.simpleName)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnViewReadyListener(onViewReadyListener: OnViewReady) {
        this.onViewReadyListener = onViewReadyListener
    }
}
