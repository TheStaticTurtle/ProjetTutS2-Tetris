package fr.iut.tetris.models;

import java.awt.*;
import java.util.Arrays;

public
class PieceModel {
	public static final Color COLOR_AQUA = Color.decode("#00FFFF");
	public static final Color COLOR_GREEN = Color.decode("#00F823");
	public static final Color COLOR_RED = Color.decode("#FF0000");
	public static final Color COLOR_PURPLE = Color.decode("#B200FF");
	public static final Color COLOR_ORANGE = Color.decode("#FFAF00");
	public static final Color COLOR_BLUE = Color.decode("#0026FF");
	public static final Color COLOR_YELLOW = Color.decode("#FFF500");

	public BlockModel[][] childs = new BlockModel[4][4];

	//Position represent top-left corner of the 4x4 grid
	int x = 0;
	int y = 0;
	Point centerOfgravity;
	Point spawnPoint; //Just for the clone()
	String name;

	public PieceModel(BlockModel[][] childs, Point spawnPoint, Point centerOfgravity, String name) {
		this.childs = childs;
		this.x = spawnPoint.x;
		this.y = spawnPoint.y;
		this.spawnPoint = spawnPoint; //Just for the clone()
		this.centerOfgravity = centerOfgravity;
		this.name = name;
		for (int y = 0; y < childs.length; y++) {
			for (int x = 0; x < childs[y].length; x++) {
				if(childs[y][x] != null) {
					childs[y][x].setParent(this);
				}
			}
		}

	}

	void spawnPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private static BlockModel[][] rotateClockWise(BlockModel[][] matrix, String name) {
		int size = matrix.length;
		BlockModel[][] ret = new BlockModel[size][size];

		if (name.equals("PieceO")) { return matrix; }

		if(name.equals("PieceI")) {
			for (int i = 0; i < size; ++i)
				for (int j = 0; j < size; ++j)
					ret[i][j] = matrix[size - j - 1][i]; //***
			return ret;
		}

		for (int i = 0; i < size-1; ++i)
			for (int j = 0; j < size-1; ++j)
				ret[i][j] = matrix[size - j - 2][i]; //***


		return ret;
	}
	private static BlockModel[][] rotateConterClockWise(BlockModel[][] matrix, String name) {
		int size = matrix.length;
		BlockModel[][] ret = new BlockModel[size][size];

		if (name.equals("PieceO")) { return matrix; }

		if(name.equals("PieceI")) {
			for (int i = 0; i < size; ++i)
				for (int j = 0; j < size; ++j)
					ret[i][j] = matrix[j][size - i -1]; //***

			return ret;
		}

		for (int i = 0; i < size-1; ++i)
			for (int j = 0; j < size-1; ++j)
				ret[i][j] = matrix[j][size - i - 2]; //***

		return ret;
	}

	void rotateModel(int direction, String name) { // -1LEFT / 1RIGHT
		if(direction == -1) {
			this.childs = rotateConterClockWise(this.childs, name);
		} else if (direction == 1){
			this.childs = rotateClockWise(this.childs, name);
		}
	}

	@Override
	protected PieceModel clone() {
		return new PieceModel(Arrays.copyOf(this.childs,this.childs.length), new Point(this.spawnPoint.x,this.spawnPoint.y), new Point(this.centerOfgravity.x,this.centerOfgravity.y), this.name);
	}

	static PieceModel PieceT = new PieceModel(
			new BlockModel[][] {

					{null						 , null						   , null						 , null},
					{new BlockModel(COLOR_PURPLE), new BlockModel(COLOR_PURPLE), new BlockModel(COLOR_PURPLE), null},
					{null                        , new BlockModel(COLOR_PURPLE), null                        , null},
					{null						 , null						   , null						 , null}
			},
			new Point(3,-1),
			new Point(3,1),
			"PieceT"
	);
	static PieceModel PieceL = new PieceModel(
			new BlockModel[][] {
					{null, new BlockModel(COLOR_ORANGE), null                        , null},
					{null, new BlockModel(COLOR_ORANGE), null                        , null},
					{null, new BlockModel(COLOR_ORANGE), new BlockModel(COLOR_ORANGE), null},
					{null, null                        , null                        , null}

			},
			new Point(3,0),
			new Point(1,2),
			"PieceL"
	);
	static PieceModel PieceJ = new PieceModel(
			new BlockModel[][] {
					{null                       , new BlockModel(COLOR_BLUE) , null, null},
					{null                       , new BlockModel(COLOR_BLUE) , null, null},
					{new BlockModel(COLOR_BLUE) , new BlockModel(COLOR_BLUE) , null, null},
					{null                       , null                       , null, null}

			},
			new Point(4,0),
			new Point(1,2),
			"PieceJ"
	);
	static PieceModel PieceO = new PieceModel(
			new BlockModel[][] {
					{null, null, null                        , null                        },
					{null, new BlockModel(COLOR_YELLOW), new BlockModel(COLOR_YELLOW), null},
					{null, new BlockModel(COLOR_YELLOW), new BlockModel(COLOR_YELLOW), null},
					{null, null, null                        , null                        }

			},
			new Point(3,-1),
			new Point(1,2),
			"PieceO"
	);
	static PieceModel PieceS = new PieceModel(
			new BlockModel[][] {
					{null                       , null                       , null                       , null},
					{null                       , new BlockModel(COLOR_GREEN), new BlockModel(COLOR_GREEN), null},
					{new BlockModel(COLOR_GREEN), new BlockModel(COLOR_GREEN), null                       , null},
					{null                       , null                       , null                       , null}

			},
			new Point(4,-1),
			new Point(1,2),
			"PieceS"
	);
	static PieceModel PieceZ = new PieceModel(
			new BlockModel[][] {
					{null                     , null                     , null                     , null},
					{new BlockModel(COLOR_RED), new BlockModel(COLOR_RED), null                     , null},
					{null                     , new BlockModel(COLOR_RED), new BlockModel(COLOR_RED), null},
					{null                     , null                     , null                     , null}

			},
			new Point(3,-1),
			new Point(1,2),
			"PieceZ"
	);
	static PieceModel PieceI = new PieceModel(
			new BlockModel[][] {
					{null                      , null                      , null                      , null},
					{null                      , null                      , null                      , null},
					{new BlockModel(COLOR_AQUA), new BlockModel(COLOR_AQUA), new BlockModel(COLOR_AQUA), new BlockModel(COLOR_AQUA)},
					{null                      , null                      , null                      , null},
			},
			new Point(3,-2),
			new Point(1,2),
			"PieceI"
	);
	static PieceModel[] Pieces = new PieceModel[]{PieceModel.PieceL, PieceModel.PieceT, PieceModel.PieceO, PieceModel.PieceS,PieceModel.PieceZ,PieceModel.PieceI,PieceModel.PieceJ};
}