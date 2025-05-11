package com.example.jetpack_compose_assignment_1
import androidx.core.view.WindowCompat
import androidx.compose.foundation.layout.statusBarsPadding
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack_compose_assignment_1.ui.theme.Jetpackcomposeassignment1Theme
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.foundation.shape.RoundedCornerShape


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure content fits within system windows (avoids drawing under status bar)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        // Set light status bar icons (dark icons)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        setContent {
            Jetpackcomposeassignment1Theme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

data class Course(
    val title: String,
    val code: String,
    val creditHours: Int,
    val description: String,
    val prerequisites: String
)

val courses = listOf(
    Course("Introduction to Computer Science", "CS101", 3, "An overview of computer science concepts.", "None"),
    Course("Data Structures", "CS201", 4, "Covers basic data structures such as lists, stacks, and trees.", "CS101"),
    Course("Algorithms", "CS301", 4, "Design and analysis of algorithms.", "CS201"),
    Course("Operating Systems", "CS302", 4, "Introduction to OS concepts including processes, threads, and memory.", "CS201"),
    Course("Database Systems", "CS303", 3, "Relational databases and SQL.", "CS201"),
    Course("Computer Networks", "CS304", 3, "Networking fundamentals, protocols, and architectures.", "CS201"),
    Course("Software Engineering", "CS305", 3, "Software development life cycle and methodologies.", "CS201"),
    Course("Artificial Intelligence", "CS306", 3, "Intro to AI techniques and applications.", "CS301"),
    Course("Machine Learning", "CS307", 3, "Supervised and unsupervised learning.", "CS306"),
    Course("Web Development", "CS308", 3, "Frontend and backend web technologies.", "CS101"),
    Course("Mobile App Development", "CS309", 3, "Developing apps for Android and iOS.", "CS308"),
    Course("Human-Computer Interaction", "CS310", 2, "User interface design principles.", "CS101"),
    Course("Cybersecurity", "CS311", 3, "Security principles and best practices.", "CS201"),
    Course("Cloud Computing", "CS312", 3, "Cloud service models and architecture.", "CS304"),
    Course("Capstone Project", "CS399", 5, "Team-based project on real-world problems.", "CS305"),
    Course("Compiler Design", "CS313", 3, "Principles of compiler construction.", "CS301")
)

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var showOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier = modifier
        .fillMaxSize()
        .statusBarsPadding()) {
        if (showOnboarding) {
            OnboardingScreen(onContinueClicked = { showOnboarding = false })
        } else {
            CourseList()
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Course List App!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun CourseList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(vertical = 10.dp)) {
        items(courses) { course ->
            CourseCard(course = course)
        }
    }
}

@Composable
fun CourseCard(course: Course, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 16.dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "extraPadding"
    )

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp), // Rounded corners
        tonalElevation = 2.dp,
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = course.title, style = MaterialTheme.typography.titleLarge)
                    Text(text = "${course.code} - ${course.creditHours} Credit Hours", style = MaterialTheme.typography.bodyLarge)
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded) "Show less" else "Show more"
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(extraPadding))
                Text(text = "Description: ${course.description}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Prerequisites: ${course.prerequisites}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    Jetpackcomposeassignment1Theme {
        MyApp(Modifier.fillMaxSize())
    }
}
