package linalg;

/*** A class that represents a two dimensional real-valued (double) matrix
 *   and supports various matrix computations required in linear algebra.
 *   
 *   Class and method comments are in JavaDoc: https://en.wikipedia.org/wiki/Javadoc
 * 
 * @author scott.sanner@utoronto.ca, <YOUR_EMAIL>
 *
 */
public class Matrix {

	private int _nRows; // Number of rows in this matrix; nomenclature: _ for data member, n for integer
	private int _nCols; // Number of columns in this matrix; nomenclature: _ for data member, n for integer
	// TODO: add your own data member to represent the matrix content
	//       you could use a 2D array, or an array of Vectors (e.g., for each row)
	private double[][] _adVals;
	/** Allocates a new matrix of the given row and column dimensions
	 * 
	 * @param rows
	 * @param cols
	 * @throws LinAlgException if either rows or cols is <= 0
	 */
	// Create a Matrix with Default ( all elements are 0.000)
	public Matrix(int rows, int cols) throws LinAlgException {
		// TODO: hint: see the corresponding Vector constructor		
		if (rows <= 0 || cols <= 0) {
			throw new LinAlgException("Both dimensions (" + rows + "," + cols +") must be greater than 0");
		}
	
		_nRows = rows;
		_nCols = cols;
		_adVals = new double[rows][cols];
	}
	
	/** Copy constructor: makes a new copy of an existing Matrix m
	 *                    (note: this explicitly allocates new memory and copies over content)
	 * 
	 * @param m
	 */
	public Matrix(Matrix m) {
		// TODO: hint: see the corresponding Vector "copy constructor" for an example
		_nRows = m._nRows;
		_nCols = m._nCols;
		_adVals = new double[_nRows][_nCols];
		// two for loops to fill the new Matrix one by one from the existing Matrix
		for (int i = 0; i < _nRows; i++) {
			for (int j = 0; j < _nCols; j++) {
				_adVals[i][j] = m._adVals[i][j];		
			}
		}
	}

	/** Constructs a String representation of this Matrix
	 * 
	 */
	public String toString() {
		// TODO: hint: see Vector.toString() for an example
		StringBuilder sb =  new StringBuilder();

		for (int i = 0; i < _nRows; i++) {
			sb.append("[");
			for (int j = 0; j < _nCols; j++) {
				sb.append(String.format(" %6.3f ", _adVals[i][j]));
			}
			sb.append(" ] \n");
		}
		return sb.toString();
	}

	/** Tests whether another Object o (most often a matrix) is a equal to *this*
	 *  (i.e., are the dimensions the same and all elements equal each other?)
	 * 
	 * @param o the object to compare to
	 */
	public boolean equals(Object o) {
		// TODO: hint: see Vector.equals(), you can also use Vector.equals() for checking equality 
		//             of row vectors if you store your matrix as an array of Vectors for rows
		if (o instanceof Matrix) {
			Matrix m = (Matrix)o;
			if (_nRows != m._nRows || _nCols != m._nCols)
				return false;
			for (int i = 0; i < _nRows; i++) {
				for (int j = 0; j < _nCols; j++) {
					if (_adVals[i][j] != m._adVals[i][j])
						return false;
				}
			}
			return true;
		} else
		// TODO: this should not always return false!
		return false; // This should not always return false!
	}
	
	/** Return the number of rows in this matrix
	 *   
	 * @return 
	 */
	public int getNumRows() {
		// TODO (this should not return -1!)
		return _nRows;
	}

	/** Return the number of columns in this matrix
	 *   
	 * @return 
	 */
	public int getNumCols() {
		// TODO (this should not return -1!)
		return _nCols;
	}

	/** Return the scalar value at the given row and column of the matrix
	 * 
	 * @param row
	 * @param col
	 * @return
	 * @throws LinAlgException if row or col indices are out of bounds
	 */
	public double get(int row, int col) throws LinAlgException {
		// TODO (this should not return -1!)
		if (row < 0 || row >= _nRows || col < 0 || col >= _nCols)
			throw new LinAlgException("One or both indices (" + row + ", " + col + ") are out of bounds ([0, " + _nRows +"], [0, " + col + "])");
		return _adVals[row][col];
	}
	
	/** Return the Vector of numbers corresponding to the provided row index
	 * 
	 * @param row
	 * @return
	 * @throws LinAlgException if row is out of bounds
	 */
	public Vector getRow(int row) throws LinAlgException {
		// TODO (this should not return null!)
		if (row < 0 || row >= _nRows)
			throw new LinAlgException("Row index (" + row + ") out of bounds [0," + _nRows + "])");
		Vector v = new Vector(_nCols);
		// to get a row, row value dont need to change, col++ 
		for (int j = 0; j < _nCols; j++) {
			v.set(j, _adVals[row][j]);
		}
		return v;
	}

	/** Set the row and col of this matrix to the provided val
	 * 
	 * @param row
	 * @param col
	 * @param val
	 * @throws LinAlgException if row or col indices are out of bounds
	 */
	public void set(int row, int col, double val) throws LinAlgException {
		// TODO
		if (row < 0 || row >= _nRows || col < 0 || col >= _nCols)
			throw new LinAlgException("One or both indices (" + row + ", " + col + ") are out of bounds ([0, " + _nRows +"], [0, " + col + "])");
		_adVals[row][col] = val;
		
	}
	
	/** Return a new Matrix that is the transpose of *this*, i.e., if "transpose"
	 *  is the transpose of Matrix m then for all row, col: transpose[row,col] = m[col,row]
	 *  (should not modify *this*)
	 * 
	 * @return
	 * @throws LinAlgException
	 */
	public Matrix transpose() throws LinAlgException {
		Matrix transpose = new Matrix(_nCols, _nRows);
		for (int row = 0; row < _nRows; row++) {
			for (int col = 0; col < _nCols; col++) {
				transpose.set(col, row, get(row,col));
			}
		}
		return transpose;
	}

	/** Return a new Matrix that is the square identity matrix (1's on diagonal, 0's elsewhere) 
	 *  with the number of rows, cols given by dim.  E.g., if dim = 3 then the returned matrix
	 *  would be the following:
	 *  
	 *  [ 1 0 0 ]
	 *  [ 0 1 0 ]
	 *  [ 0 0 1 ]
	 * 
	 * @param dim
	 * @return
	 * @throws LinAlgException if the dim is <= 0
	 */
	public static Matrix GetIdentity(int dim) throws LinAlgException {
		// TODO: this should not return null!
		if (dim <= 0) 
			throw new LinAlgException("Size " + dim + " must be greater than 0");
		Matrix identityMatrix = new Matrix(dim,dim);
		// IdentityMatrix only row == col, the value is 1, others remain 0
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (i == j) {
					identityMatrix._adVals[i][j] = 1;
				}
			}
		}
		
		return identityMatrix;
	}
	
	/** Returns the Matrix result of multiplying Matrix m1 and m2
	 *  (look up the definition of matrix multiply if you don't remember it)
	 * 
	 * @param m1
	 * @param m2
	 * @return
	 * @throws LinAlgException if m1 columns do not match the size of m2 rows
	 */
	public static Matrix Multiply(Matrix m1, Matrix m2) throws LinAlgException{
		// TODO: this should not return null!
		if (m1._nCols != m2._nRows)
			throw new LinAlgException("Cannot multiply matrix m1 having " + m1._nCols + " colums with matrix m2 having " + m2._nRows +"rows");
		// the value of m1's row is the new matrix's row, m2's col is the new matrix's col 
		Matrix newMatrixMult = new Matrix(m1._nRows, m2._nCols);
		// define which row from m1 is being used
		for (int i = 0; i < m1._nRows; i++) {
			//which col from m2 is being used
			for (int j = 0; j < m2._nCols; j++) {
				// how many elements should time their corresponding elements
				for (int k = 0; k < m2._nRows; k++) {
					newMatrixMult._adVals[i][j] += m1._adVals[i][k]*m2._adVals[k][j];
				}
			}
		}	
		return newMatrixMult;
	}
		
	/** Returns the Vector result of multiplying Matrix m by Vector v (assuming v is a column vector)
	 * 
	 * @param m
	 * @param v
	 * @return
	 * @throws LinAlgException if m columns do match the size of v
	 */
	public static Vector Multiply(Matrix m, Vector v) throws LinAlgException {
		// TODO: this should not return null!
		// same idea of matrix mult
		if (v.getDim() != m._nCols)
			throw new LinAlgException("Cannot multiply matrix with " + m._nCols + " columns with a vector of dimension " + v.getDim());
		Vector newMixedMult = new Vector(m._nRows);
		double sum = 0;
		for (int i = 0; i < m._nRows; i++) {
			for (int j = 0; j < v.getDim(); j++) {
				 sum += m._adVals[i][j]*v.get(j);
			}
			newMixedMult.set(i, sum);
			sum = 0;
		}
		return newMixedMult;
	}

}
