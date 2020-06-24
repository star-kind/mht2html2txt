#git init

#git config user.email "duernna@163.com"
#git config user.name  "AllStarGH"

#origin:远方仓库的名字
#git remote add origin https://github.com/AllStarGH/mht2html2txt.git

git status

git add .  

git commit -m "it solves the problem of disorderly code in reading and writing Chinese characters"

#如果第一次提交报错:更新被拒绝,因为您当前分支的最新提交落后于其对应的远程分支
#可以再试一次,也是下面这句话
#git pull origin master --allow-unrelated-histories

#master:当前分支名
#首次上传成功后,就切换为下面这句
git pull origin master

git push origin master  
