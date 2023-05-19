USE [master]
GO
/****** Object:  Database [DB_dacsanmientrung]    Script Date: 5/19/2023 5:03:17 PM ******/
CREATE DATABASE [DB_dacsanmientrung]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DB_dacsanmientrung', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.VODKA\MSSQL\DATA\DB_dacsanmientrung.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'DB_dacsanmientrung_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.VODKA\MSSQL\DATA\DB_dacsanmientrung_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [DB_dacsanmientrung] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DB_dacsanmientrung].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DB_dacsanmientrung] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET ARITHABORT OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DB_dacsanmientrung] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DB_dacsanmientrung] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET  ENABLE_BROKER 
GO
ALTER DATABASE [DB_dacsanmientrung] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DB_dacsanmientrung] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET RECOVERY FULL 
GO
ALTER DATABASE [DB_dacsanmientrung] SET  MULTI_USER 
GO
ALTER DATABASE [DB_dacsanmientrung] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DB_dacsanmientrung] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DB_dacsanmientrung] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DB_dacsanmientrung] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DB_dacsanmientrung] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DB_dacsanmientrung] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'DB_dacsanmientrung', N'ON'
GO
ALTER DATABASE [DB_dacsanmientrung] SET QUERY_STORE = OFF
GO
USE [DB_dacsanmientrung]
GO
/****** Object:  Table [dbo].[BINHLUAN]    Script Date: 5/19/2023 5:03:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BINHLUAN](
	[maBL] [bigint] IDENTITY(1,1) NOT NULL,
	[noiDung] [ntext] NOT NULL,
	[ngayTao] [datetime] NOT NULL,
	[maSP] [bigint] NOT NULL,
	[taiKhoan] [varchar](15) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maBL] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CHITIETDONHANG]    Script Date: 5/19/2023 5:03:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CHITIETDONHANG](
	[maDon] [bigint] NOT NULL,
	[maSP] [bigint] NOT NULL,
	[dongia] [float] NOT NULL,
	[soLuong] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maDon] ASC,
	[maSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CHUCVU]    Script Date: 5/19/2023 5:03:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CHUCVU](
	[maCV] [int] NOT NULL,
	[tenCV] [nvarchar](250) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maCV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DONHANG]    Script Date: 5/19/2023 5:03:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DONHANG](
	[maDon] [bigint] IDENTITY(1,1) NOT NULL,
	[ngayTao] [date] NOT NULL,
	[trangThai] [bit] NOT NULL,
	[tong] [float] NOT NULL,
	[taiKhoan] [varchar](15) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maDon] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HINHANH]    Script Date: 5/19/2023 5:03:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HINHANH](
	[maHinh] [bigint] IDENTITY(1,1) NOT NULL,
	[tenhinh] [nvarchar](250) NOT NULL,
	[maSP] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maHinh] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LOAISP]    Script Date: 5/19/2023 5:03:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LOAISP](
	[maLoai] [varchar](15) NOT NULL,
	[tenLoai] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maLoai] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SANPHAM]    Script Date: 5/19/2023 5:03:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SANPHAM](
	[maSP] [bigint] IDENTITY(1,1) NOT NULL,
	[tenSP] [nvarchar](50) NOT NULL,
	[soLuong] [int] NOT NULL,
	[dongia] [float] NOT NULL,
	[moTa] [nvarchar](250) NOT NULL,
	[maLoai] [varchar](15) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[maSP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TAIKHOAN]    Script Date: 5/19/2023 5:03:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TAIKHOAN](
	[taiKhoan] [varchar](15) NOT NULL,
	[matKhau] [varchar](15) NOT NULL,
	[hoTen] [nvarchar](15) NOT NULL,
	[email] [varchar](15) NOT NULL,
	[sdt] [varchar](20) NOT NULL,
	[diaChi] [varchar](15) NOT NULL,
	[ngaySinh] [date] NULL,
	[maCV] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[taiKhoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (1, 8, 40000, 15)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (1, 13, 30000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (1, 18, 40000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (1, 22, 500000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 1, 600000, 10)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 2, 500000, 18)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 3, 30000, 20)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 4, 800000, 40)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 5, 50000, 50)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 6, 90000, 20)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 7, 600000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 14, 800000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 15, 50000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 23, 50000, 70)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (2, 24, 90000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (3, 1, 600000, 30)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (3, 12, 600000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (10009, 18, 50000, 2)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (10010, 24, 130000, 3)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (10011, 27, 150000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (10012, 27, 150000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (10013, 10, 65000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (10014, 10, 65000, 1)
INSERT [dbo].[CHITIETDONHANG] ([maDon], [maSP], [dongia], [soLuong]) VALUES (10015, 27, 150000, 2)
GO
INSERT [dbo].[CHUCVU] ([maCV], [tenCV]) VALUES (1, N'Khách hàng')
INSERT [dbo].[CHUCVU] ([maCV], [tenCV]) VALUES (2, N'Nhân viên')
INSERT [dbo].[CHUCVU] ([maCV], [tenCV]) VALUES (3, N'Quản lý')
GO
SET IDENTITY_INSERT [dbo].[DONHANG] ON 

INSERT [dbo].[DONHANG] ([maDon], [ngayTao], [trangThai], [tong], [taiKhoan]) VALUES (1, CAST(N'2022-10-28' AS Date), 1, 10000000, N'TeoNV')
INSERT [dbo].[DONHANG] ([maDon], [ngayTao], [trangThai], [tong], [taiKhoan]) VALUES (2, CAST(N'2022-10-25' AS Date), 1, 600000, N'TeoNV')
INSERT [dbo].[DONHANG] ([maDon], [ngayTao], [trangThai], [tong], [taiKhoan]) VALUES (3, CAST(N'2022-10-20' AS Date), 1, 60000, N'TaiBT')
INSERT [dbo].[DONHANG] ([maDon], [ngayTao], [trangThai], [tong], [taiKhoan]) VALUES (10009, CAST(N'2022-11-26' AS Date), 0, 100000, N'HungTH')
INSERT [dbo].[DONHANG] ([maDon], [ngayTao], [trangThai], [tong], [taiKhoan]) VALUES (10010, CAST(N'2022-11-26' AS Date), 1, 130000, N'HungTH')
INSERT [dbo].[DONHANG] ([maDon], [ngayTao], [trangThai], [tong], [taiKhoan]) VALUES (10011, CAST(N'2022-11-26' AS Date), 1, 150000, N'HungTH')
INSERT [dbo].[DONHANG] ([maDon], [ngayTao], [trangThai], [tong], [taiKhoan]) VALUES (10012, CAST(N'2022-11-26' AS Date), 1, 150000, N'TaiBT')
INSERT [dbo].[DONHANG] ([maDon], [ngayTao], [trangThai], [tong], [taiKhoan]) VALUES (10013, CAST(N'2022-11-26' AS Date), 1, 65000, N'TaiBT')
INSERT [dbo].[DONHANG] ([maDon], [ngayTao], [trangThai], [tong], [taiKhoan]) VALUES (10014, CAST(N'2022-11-26' AS Date), 1, 65000, N'TaiBT')
INSERT [dbo].[DONHANG] ([maDon], [ngayTao], [trangThai], [tong], [taiKhoan]) VALUES (10015, CAST(N'2022-11-26' AS Date), 1, 150000, N'TaiBT')
SET IDENTITY_INSERT [dbo].[DONHANG] OFF
GO
SET IDENTITY_INSERT [dbo].[HINHANH] ON 

INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (1, N'hinh1.jpg', 1)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (2, N'hinh2.jpg', 2)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (3, N'hinh3.jpg', 3)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (4, N'hinh4.jpg', 4)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (5, N'hinh5.jpg', 5)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (6, N'hinh6.jpg', 6)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (7, N'hinh7.jpg', 7)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (8, N'hinh8.jpg', 8)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (9, N'hinh9.jpg', 9)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (10, N'hinh10.jpg', 10)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (11, N'hinh11.jpg', 11)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (12, N'hinh12.jpg', 12)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (13, N'hinh13.jpg', 13)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (14, N'hinh14.jpg', 14)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (15, N'hinh15.jpg', 15)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (16, N'hinh16.jpg', 16)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (17, N'hinh17.jpg', 17)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (18, N'hinh18.jpg', 18)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (19, N'hinh19.jpg', 19)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (20, N'hinh20.jpg', 20)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (21, N'hinh11.jpg', 21)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (22, N'hinh22.jpg', 22)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (23, N'hinh23.jpg', 23)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (24, N'hinh24.jpg', 24)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (25, N'hinh25.jpg', 25)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (26, N'hinh26.jpg', 26)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (27, N'hinh27.jpg', 27)
INSERT [dbo].[HINHANH] ([maHinh], [tenhinh], [maSP]) VALUES (10030, N'of4_1.png', 10004)
SET IDENTITY_INSERT [dbo].[HINHANH] OFF
GO
INSERT [dbo].[LOAISP] ([maLoai], [tenLoai]) VALUES (N'a', N'a')
INSERT [dbo].[LOAISP] ([maLoai], [tenLoai]) VALUES (N'A1', N'Đặc sản miền Trung')
INSERT [dbo].[LOAISP] ([maLoai], [tenLoai]) VALUES (N'A2', N'Mắm miền Trung')
INSERT [dbo].[LOAISP] ([maLoai], [tenLoai]) VALUES (N'A3', N'Bánh kẹo miền Trung')
INSERT [dbo].[LOAISP] ([maLoai], [tenLoai]) VALUES (N'A4', N'Trà - Rượu')
GO
SET IDENTITY_INSERT [dbo].[SANPHAM] ON 

INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (1, N'Bò khô Đà Nẵng', 100, 600000, N'Bò khô Đà Nẵng', N'A1')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (2, N'Khô Nai', 100, 500000, N'Khô Nai Đà Nẵng', N'A1')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (3, N'Mạch nha Thiên Bút', 100, 30000, N'Đặc sản Quảng Ngãi', N'A1')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (4, N'Tỏi cô đơn Lý Sơn', 100, 800000, N'Đặc sản Quảng Ngãi', N'A1')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (5, N'Đường Phèn', 99, 50000, N'Đặc sản Quảng Ngãi', N'A1')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (6, N'Bánh tráng mè đen', 100, 90000, N'Đặc sản  Phan Rang', N'A1')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (7, N'Bánh tráng Đại Lộc', 100, 50000, N'Đặc sản Quảng Nam', N'A2')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (8, N'Bánh trang Khoai lang', 100, 40000, N'Đặc sản Nha Trang', N'A1')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (9, N'Yến sào Khánh Hòa', 100, 80000, N'Đặc sản Nha Trang Khánh Hòa', N'A1')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (10, N'Nước mắm Nam Ô', 98, 65000, N'Đặc sản Đà Nẵng', N'A2')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (11, N'Mắm cá rò', 100, 80000, N'Đặc sản Thừa Thiên Huế', N'A2')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (12, N'Mắm ruốc', 100, 30000, N'Đặc sản Thừa Thiên Huế', N'A2')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (13, N'Mắm tôm chua', 100, 90000, N'Đặc sản Thừa Thiên Huế', N'A2')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (14, N'Mắm cá cơm thu ', 100, 60000, N'Đặc sản Đà Nẵng', N'A2')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (15, N'Mắm dưa đèo', 100, 40000, N'Đặc sản Đà Nẵng', N'A2')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (16, N'Bánh khô mè Bà Liễu', 100, 70000, N'Đặc sản Đà Nẵng', N'A3')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (17, N'Bánh nổ dẻo', 100, 30000, N'Đặc sản Quảng Ngãi', N'A3')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (18, N'Bánh hạt sen', 100, 50000, N'Đặc sản Thừa Thiên Huế', N'A3')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (19, N'Bánh dừa nướng', 100, 15000, N'Đặc sản Quảng Nam', N'A3')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (20, N'Bánh dừa dẻo', 100, 50000, N'Đặc sản Quảng Nam', N'A3')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (21, N'Bánh Phục Linh', 100, 40000, N'Đặc sản Quảng Ngãi', N'A3')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (22, N'Bánh đậu xanh nhân thịt', 100, 30000, N'Đặc sản Quãng Nam', N'A3')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (23, N'Rượu hồng đào', 100, 230000, N'Đặc sản Quãng Nam', N'A4')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (24, N'Rượu Bàu đá', 96, 130000, N'Đặc sản Bình Định', N'A4')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (25, N'Trà hoàng đế', 98, 50000, N'Đặc sản Thừa Thiên Huế ', N'A4')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (26, N'Trà quý phi', 98, 130000, N'Đặc sản Thừa Thiên Huế ', N'A4')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (27, N'Trà Atiso', 93, 150000, N'Đặc sản Đà Lạt Lâm Đồng', N'A4')
INSERT [dbo].[SANPHAM] ([maSP], [tenSP], [soLuong], [dongia], [moTa], [maLoai]) VALUES (10004, N'a', 100, 20000, N'aaaa', N'a')
SET IDENTITY_INSERT [dbo].[SANPHAM] OFF
GO
INSERT [dbo].[TAIKHOAN] ([taiKhoan], [matKhau], [hoTen], [email], [sdt], [diaChi], [ngaySinh], [maCV]) VALUES (N'a2', N'a', N'a', N'a@gmail.com', N'1111', N'a', CAST(N'2022-12-08' AS Date), 1)
INSERT [dbo].[TAIKHOAN] ([taiKhoan], [matKhau], [hoTen], [email], [sdt], [diaChi], [ngaySinh], [maCV]) VALUES (N'HungTH', N'123', N'Trần Huy Hưng', N'hung@gmail.com', N'123456789', N'Ð?ng Nai', CAST(N'1999-06-09' AS Date), 2)
INSERT [dbo].[TAIKHOAN] ([taiKhoan], [matKhau], [hoTen], [email], [sdt], [diaChi], [ngaySinh], [maCV]) VALUES (N'KhoaND', N'123', N'Nguyễn Đ.Khoa', N'khoa@gmail.com', N'123456789', N'TPHCM', CAST(N'1999-10-10' AS Date), 2)
INSERT [dbo].[TAIKHOAN] ([taiKhoan], [matKhau], [hoTen], [email], [sdt], [diaChi], [ngaySinh], [maCV]) VALUES (N'NVA1999', N'123', N'Nguyễn Văn A', N'vana@gmail.com', N'123456789', N'TPHCM', CAST(N'1999-10-10' AS Date), 1)
INSERT [dbo].[TAIKHOAN] ([taiKhoan], [matKhau], [hoTen], [email], [sdt], [diaChi], [ngaySinh], [maCV]) VALUES (N'PhiT', N'123', N'Trần Phi', N'phi@gmail.com', N'123456789', N'TPHCM', CAST(N'1999-10-10' AS Date), 3)
INSERT [dbo].[TAIKHOAN] ([taiKhoan], [matKhau], [hoTen], [email], [sdt], [diaChi], [ngaySinh], [maCV]) VALUES (N'TaiBT', N'123', N'Bùi Tiến Tài', N'tai@gmail.com', N'123456789', N'Vung Tàu', CAST(N'1999-10-10' AS Date), 1)
INSERT [dbo].[TAIKHOAN] ([taiKhoan], [matKhau], [hoTen], [email], [sdt], [diaChi], [ngaySinh], [maCV]) VALUES (N'TeoNV', N'123', N'Nguyễn Văn Tèo', N'Teo@gmail.com', N'123456789', N'TPHCM', CAST(N'1989-10-01' AS Date), 1)
INSERT [dbo].[TAIKHOAN] ([taiKhoan], [matKhau], [hoTen], [email], [sdt], [diaChi], [ngaySinh], [maCV]) VALUES (N'TienNLD', N'123', N'Nguyễn L D Tiến', N'tien@gmail.com', N'123456789', N'TPHCM', CAST(N'1999-10-10' AS Date), 3)
GO
ALTER TABLE [dbo].[BINHLUAN]  WITH CHECK ADD FOREIGN KEY([maSP])
REFERENCES [dbo].[SANPHAM] ([maSP])
GO
ALTER TABLE [dbo].[BINHLUAN]  WITH CHECK ADD FOREIGN KEY([taiKhoan])
REFERENCES [dbo].[TAIKHOAN] ([taiKhoan])
GO
ALTER TABLE [dbo].[CHITIETDONHANG]  WITH CHECK ADD  CONSTRAINT [PK_CHTD2] FOREIGN KEY([maSP])
REFERENCES [dbo].[SANPHAM] ([maSP])
GO
ALTER TABLE [dbo].[CHITIETDONHANG] CHECK CONSTRAINT [PK_CHTD2]
GO
ALTER TABLE [dbo].[CHITIETDONHANG]  WITH CHECK ADD  CONSTRAINT [PK_CTDH1] FOREIGN KEY([maDon])
REFERENCES [dbo].[DONHANG] ([maDon])
GO
ALTER TABLE [dbo].[CHITIETDONHANG] CHECK CONSTRAINT [PK_CTDH1]
GO
ALTER TABLE [dbo].[DONHANG]  WITH CHECK ADD FOREIGN KEY([taiKhoan])
REFERENCES [dbo].[TAIKHOAN] ([taiKhoan])
GO
ALTER TABLE [dbo].[HINHANH]  WITH CHECK ADD  CONSTRAINT [PK_HinhAnh] FOREIGN KEY([maSP])
REFERENCES [dbo].[SANPHAM] ([maSP])
GO
ALTER TABLE [dbo].[HINHANH] CHECK CONSTRAINT [PK_HinhAnh]
GO
ALTER TABLE [dbo].[SANPHAM]  WITH CHECK ADD  CONSTRAINT [PK_Maloai] FOREIGN KEY([maLoai])
REFERENCES [dbo].[LOAISP] ([maLoai])
GO
ALTER TABLE [dbo].[SANPHAM] CHECK CONSTRAINT [PK_Maloai]
GO
ALTER TABLE [dbo].[TAIKHOAN]  WITH CHECK ADD  CONSTRAINT [PK_CV] FOREIGN KEY([maCV])
REFERENCES [dbo].[CHUCVU] ([maCV])
GO
ALTER TABLE [dbo].[TAIKHOAN] CHECK CONSTRAINT [PK_CV]
GO
USE [master]
GO
ALTER DATABASE [DB_dacsanmientrung] SET  READ_WRITE 
GO
