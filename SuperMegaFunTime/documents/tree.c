/*
Paul Nguyen
COMP350

DISCRIPTION: Draws a white mustache.
*/
#include <stdio.h>
#include <math.h>						//Imports Math functions
#include <GL/glut.h>					// include GL Utility Toolkit
#include <stdlib.h>						// for atof()

int p = 0;

void init(void)
{
	/* Set up the clearing color to black; clearing color means
	   background color. Note that the fourth element of color
	   is transparency */
	glClearColor(0.0, 0.0, 0.0, 0.0);

	/* The four following statements set up the viewing rectangle */
	glMatrixMode(GL_PROJECTION);		//  use proj. matrix
	glLoadIdentity();					// load identity matrix
	gluOrtho2D(-100, 100, -100, 100);	// set orthographic projection
	/*
	   We could have set the projection in "screen coordinates", e.g.,
	   gluOrtho2D(0.0, 700.0, 0.0, 700.0);
	*/
	glMatrixMode(GL_MODELVIEW);			// back to modelview matrix
}

void treeMethod(double x1, double y1, double x2, double y2, double n){
	//Create the square using the two xy cordinates I passed in
	double x3, y3, x4, y4, x5, y5, dist, pixel;
	//Find the bottom right point
	x3 = (x2 - (y1 - y2));
	y3 = (y2 + (x1 - x2));
	//Find the bottom left point
	x4 = x3 + (x1 - x2);
	y4 = y3 + (y1 - y2);
	//Find the fifth point
	x5 = x4 + ((x3 - x1)/2);
	y5 = y4 + ((y3 - y1)/2);

	//Fill in the four points
	glBegin(GL_POLYGON);
	glColor3f(n, n, n);

	glVertex3f(x1, y1, 0.0);
	glVertex3f(x2, y2, 0.0);
	glVertex3f(x3, y3, 0.0);
	glVertex3f(x4, y4, 0.0);
	glEnd();

	dist = sqrt(((x2-x1)*(x2-x1)) + ((y2 - y1)*(y2 - y1)));
	pixel = (200.0/700.0);
	p++;
	//printf("Hello World\n");
	if(dist > pixel){
		//printf("Hello World\n");
		treeMethod(x4, y4, x5, y5, n+0.05);
		treeMethod(x5, y5, x3, y3, n+0.05);
	}


	/*This actually makes a speech bubble
	//Fill in the four points
	glBegin(GL_POLYGON);
	glBegin(GL_POINTS);
	glVertex3f(x1, y1, 0.0);
	glVertex3f(x2, y2, 0.0);
	glVertex3f(x3, y3, 0.0);
	glVertex3f(x4, y4, 0.0);
	glVertex3f(x5, y5, 0.0);
	*/



}

/* The display callback function. It will be called ("automatically")
   when there is a need to display the window */
void ourDisplay(void)
{
	/* Clear the color buffer, i.e., fill it with clearing color */
	glClear(GL_COLOR_BUFFER_BIT);

	treeMethod(-10, 10, 10, 10, 0.0); //Call the tree method that I wrote

	/* Flush the picture to the screen */
	glFlush();
}

void ourReshape3(int w, int h)
{
	GLfloat aspectRatio = 100.0;

	/* Compute the aspect ratio of the resized window */
	aspectRatio = (GLfloat)h / (GLfloat)w;
	
	/* Adjust the clipping box */
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	if (h >= w)
		gluOrtho2D(-100.0, 100.0, -aspectRatio, aspectRatio);
	else
		gluOrtho2D(-100.0/aspectRatio, 100.0/aspectRatio, -100.0, 100.0);
	glMatrixMode(GL_MODELVIEW);

	/* Now we have to adjust the viewport */
	glViewport(0, 0, w, h);
}

void myMouse(int butt, int state, int x, int y)
{
	if (state == GLUT_DOWN && butt == GLUT_RIGHT_BUTTON)
		exit(0);
}

void myKeyboard(char key, int x, int y)
{
	float val;
	/* Capture Escape and exit the program */
	if ((int)key == 27)
		exit(0);
}

/* The main function */
int main(int argc, char *argv[]){
	///////////////////////////////////Boiler Plate Stuff/////////////////////////////////////


	/* Establish communication between OpenGL and the windowing
	system */
	glutInit(&argc, argv);

	/* Set up display mode; in this case it is single buffering
	   (display goes directly to the screen) and the RGB color
	   mode. */
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(700, 700);		// Size of window on screen
	glutInitWindowPosition(0, 0);		// Where the window appears on screen
	glutCreateWindow("WHITE MUSTACHe");		// window title


	///////////////////////////////////Boiler Plate Stuff/////////////////////////////////////


	/* The following is the callback handler */
	/* aka This is where all the computer drawing happens*/
	glutDisplayFunc(ourDisplay);

	glutMouseFunc(myMouse);
	glutKeyboardFunc(myKeyboard);
	glutReshapeFunc(ourReshape3);

	/* If whichReshape is 0, a default reshape callback is used;
	   see yourself */

	init();
	/* Start the main loop that waits for events to happen and
	   then to process them */
	glutMainLoop();
}
