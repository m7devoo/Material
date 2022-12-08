# Material (BottomNavigation)

& How to get a Git project into your build:
   Step 1. Add the JitPack repository to your build file
   Add it in your root build.gradle at the end of repositories:
        
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
   Step 2. Add the dependency

	dependencies {
		implementation 'com.github.m7devoo:Material:1.0'
	}
	
& How to use:

	@Composable
	fun TestPreview() {
		BottomNavigation(
			modifier = Modifier
			    .padding(4.dp),
			painters = arrayListOf(
			    rememberVectorPainter(image = Icons.Rounded.Home),
			    rememberVectorPainter(image = Icons.Rounded.AccountCircle),
			    rememberVectorPainter(image = Icons.Rounded.Info),
			    rememberVectorPainter(image = Icons.Rounded.Edit),
				rememberVectorPainter(image = Icons.Rounded.Settings),
			),
			radius = 20f,
			background = Color.Black,
			iconTint = Color.Gray,
			defaultSelected = 4
		)
	}

& Preview:
   
   ![Preview](/images/bottom-navigation.gif)



& By:   m7devoo ♥
