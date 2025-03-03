import com.dunice.advancedServiceKotlin.models.Tag
import com.dunice.advancedServiceKotlin.models.User
import jakarta.persistence.*

@Entity
@Table(name = "news", schema = "news_schema")
 class News(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id", nullable = false)
    var newsId: Long=0L,

    @Column(name = "description", nullable = false)
    var description: String="",

    @Column(name = "image", nullable = false)
    var image: String="",

    @Column(name = "title", nullable = false)
    var title: String="",

    @ManyToMany
    @JoinTable(
        name = "news_tags",
        joinColumns = [JoinColumn(name = "news_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: Set<Tag> = HashSet(),

    @ManyToOne
    @JoinColumn(name = "users_id")
    var author: User,
)
