# BtcTurk Case Study

## Introduction

CryptoCaseStudy is a cryptocurrency tracking application with a two-page layout:
- **Home Page**: Displays a list of available cryptocurrencies, allowing users to add and remove favorites.
- **Chart Page**: Shows real-time charts for selected cryptocurrencies, providing in-depth data visualization.

The application utilizes Clean Architecture featured based modular structure with MVVM principles for structured, maintainable code. Some key features include Shimmer Loading, Pull-to-Refresh, and user-friendly error handling.

## Technology Stack

### Libraries
- **Kotlin**: Primary language for Android development
- **Android Jetpack**: Core libraries, including Navigation, Room, and ViewModel
- **Room**: Local database for caching favorite coins
- **Material Design**: Consistent, responsive, and accessible UI components
- **Hilt**: Dependency injection for efficient and maintainable code
- **OkHttp & Retrofit**: Networking libraries for API calls and data handling
- **Coroutines**: Asynchronous operations and multithreading


## Key Functionalities
- **List Display**: The main screen displays a list of cryptocurrency pairs and favorites list, while the second screen provides detailed charts for each selected coin.
- **Pull-to-Refresh**: Easily refresh data on the main screen with a swipe gesture
- **Favorite Management**: Add or remove coins from the favorites list for quick access
- **Error Handling**: User-friendly feedback for network and data issues
- **Shimmer Effect**: Loading animations to enhance UX, used for displaying placeholders during data load
- **MPAndroidChart**: Data visualization for cryptocurrency charts
- **MVVM Architecture**: Ensures a well-organized codebase and separation of concerns, promoting maintainability


## Project Structure

The project adheres to the Clean Architecture principles with a featured-based modular structure and MVVM pattern, dividing responsibilities across the following modules:

1. **Data Layer**: Contains Room database entities, DAOs, and Retrofit API services
2. **Domain Layer**: Use cases encapsulating core business logic
3. **Presentation Layer**: ViewModels, UI components, and fragment implementations

## Features

### Key Functionalities
- **Pull-to-Refresh**: Swipe down to refresh data on the main screen
- **Error Handling**: Comprehensive error handling with user feedback for network or data issues
- **Shimmer Effect**: Displays shimmer-loading animations to enhance UX
- **MVVM Architecture**: Ensures separation of concerns with well-structured code

### Architectural Highlights
- **Clean Architecture**: Organized into Domain, Data, and Presentation layers for enhanced maintainability and testability
- **Feature-Based Structure**: Each major feature is encapsulated within its package, along with a core package that provides shared resources and utilities, promoting modularity and reducing code duplication

## Development Workflow

### Git Flow Strategy

This project utilizes the Git Flow branching model to manage development efficiently. Git Flow provides a robust framework for handling features, releases, and hotfixes, making it ideal for the structured release cycle. I chose Git Flow over GitHub Flow due to its support for parallel development and dedicated release preparation. For detailed information about the Git Flow implementation, please refer to the [Git Flow documentation](docs/git_flow.md).

### Commit Message Standards

I adhere to strict commit message standards to maintain a clear and informative Git history. My commit messages follow a specific format, enforced by a `commit-msg` Git hook. This approach ensures consistency across the project, facilitating easier code reviews and automated changelog generation. For a comprehensive guide on commit message format and examples, please see the [Commit Standards documentation](docs/commit_standard.md).


