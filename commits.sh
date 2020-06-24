#git init

git config user.email "duernna@163.com"
git config user.name  "AllStarGH"

#origin:远端仓库的名字
#git remote add origin https://gitee.com/yuanjingpeng/mht2html2txt.git
git remote add origin https://github.com/AllStarGH/mht2html2txt.git

git status

git add .  

git commit -m "it solves the problem of disorderly code in reading and writing Chinese characters"

#首次提交报错:fatal: 拒绝合并无关的历史
git pull origin master --allow-unrelated-histories

#master:当前分支名
#git pull origin master

git push origin master  
